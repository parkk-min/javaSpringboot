package com.example.madang_project.data.repository;

import com.example.madang_project.data.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEntityRepository extends JpaRepository<BookEntity, Integer> {
}
