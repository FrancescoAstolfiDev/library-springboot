package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired // inject directly the dependency from the repository
    private BookRepository bookRepository;

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
}
