package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Combine @Controller and @ResponseBody for return a Json
@RequestMapping("/api/books") // Map of the url for all the methods of the class, its the router
public class BookController {

    @Autowired // Buisness logic automatically use singleton dependency
    private BookService bookService;

    // it return the whole list of books
    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.findAll();
    }

    // GET /api/books/{id} it return the single instance of books with that id
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {  // extract the id from the url
        return bookService.findById(id)
                .map(ResponseEntity::ok) // return 200 ok if it find the book
                .orElse(ResponseEntity.notFound().build()); //  404 Not Found  if not exist
    }

    @GetMapping("/search") // dinamic endpoint
    public ResponseEntity<List<BookDTO>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {

        List<BookDTO> results = bookService.findBooksDynamic(title, author);
        return ResponseEntity.ok(results);
    }


    // POST /api/books it create a new book
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO book) { // deserialize the json in input
        BookDTO savedBook = bookService.save(book); // save the new data
        return ResponseEntity.status(201).body(savedBook); // it return the 201 created or nu
    }


}