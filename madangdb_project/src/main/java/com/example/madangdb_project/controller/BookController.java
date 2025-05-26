package com.example.madangdb_project.controller;

import com.example.madangdb_project.data.dto.BookDTO;
import com.example.madangdb_project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping(value = "/bookinfo/{id}")
    public ResponseEntity<BookDTO> getBookInfo(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookInfo(id));
    }
}
