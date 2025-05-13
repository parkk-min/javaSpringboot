package com.example.demo2.controller;

import com.example.demo2.data.Book;
import com.example.demo2.data.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/booklist")
    public List<Book> bookList() {
        return this.bookRepository.findAll();
    }

    @GetMapping(value = "/book/{id}")
    public Book book(@PathVariable Integer id) {
        Optional<Book> book = this.bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        return null;
    }

    @PostMapping(value = "/book")
    public Book addBook(@RequestBody Book book) {
        return this.bookRepository.save(book);
    }

    @DeleteMapping(value = "/book/{id}")
    public String deleteBook(@PathVariable Integer id) {
        if (this.bookRepository.existsById(id)) {
            this.bookRepository.deleteById(id);
            return "Book deleted successfully";
        }
        return "Book not found";
    }
}
