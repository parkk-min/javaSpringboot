package com.example.todo_list.data.repository;

import com.example.todo_list.data.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDORepository extends JpaRepository<ToDo, Integer> {
}
