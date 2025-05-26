package com.example.madangdb_project.data.dao;

import com.example.madangdb_project.data.entity.BookEntity;
import com.example.madangdb_project.data.repository.BookEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookDAO {
    private final BookEntityRepository bookEntityRepository;

    public BookEntity getBookInfo(Integer id){
        Optional<BookEntity> bookEntity = bookEntityRepository.findById(id);
        return bookEntity.orElse(null);
    }
}
