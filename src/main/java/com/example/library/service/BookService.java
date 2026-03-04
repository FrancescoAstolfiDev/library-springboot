package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired // inject directly the dependency from the repository
    private BookRepository bookRepository;
    @PersistenceContext
    private EntityManager entityManager;// handled from spring
    // Return the list of all the books
    public List<BookDTO> findAll(){
        return bookRepository.findAll().stream().map(BookDTO::new).toList();
    }

    // Use the Optional type for avoid nullPointerException
    public Optional<BookDTO> findById(Long id) {
        return bookRepository.findById(id).map(BookDTO::new);
    }

    // Store a new Book on the database and return the updated version
    public BookDTO save(BookDTO bookDTO){
        Book book = bookDTO.toEntity();
        return new BookDTO(bookRepository.save(book));
    }

    public List<BookDTO> findBooksDynamic(String title, String author) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            predicates.add(cb.like(book.get("title"), "%" + title + "%"));
        }
        if (author != null && !author.isEmpty()) {
            predicates.add(cb.equal(book.get("author"), author));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        List<Book> result= entityManager.createQuery(cq).getResultList();
        return result.stream()
                .map(BookDTO::new)
                .toList();
    }
}
