package com.example.library.dto;

import com.example.library.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private int year;
    public Book toEntity() {
        Book book = new Book();
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setYear(this.year);
        return book;
    }
    public BookDTO(Optional<Book> optionalBook) {
        optionalBook.ifPresent(book -> {
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.year = book.getYear();
        });
    }
    public BookDTO(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.year = book.getYear();
    }
}