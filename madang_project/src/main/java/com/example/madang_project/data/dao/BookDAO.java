package com.example.madang_project.data.dao;

import com.example.madang_project.data.entity.BookEntity;
import com.example.madang_project.data.repository.BookEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookDAO {
    private final BookEntityRepository bookEntityRepository;

    public BookEntity getBookById(Integer id) {
        Optional<BookEntity> bookEntity = bookEntityRepository.findById(id);
        return bookEntity.orElse(null);
    }

}
