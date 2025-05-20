package com.example.todo_list.controller;

import com.example.todo_list.data.dto.ToDoDTO;
import com.example.todo_list.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping(value = "/todo-list")
    public ResponseEntity<List<ToDoDTO>> getTodoList() {
        List<ToDoDTO> toDoDTOList = this.toDoService.getAll();
        return ResponseEntity.ok().body(toDoDTOList);
    }

    @PostMapping(value = "/add-todo")
    public ResponseEntity<ToDoDTO> saveToDo(@RequestBody ToDoDTO toDoDTO) {
        ToDoDTO savedToDoDTO = this.toDoService.saveToDo(toDoDTO);
        return ResponseEntity.ok().body(savedToDoDTO);
    }

    @PutMapping(value = "/todo-list/{id}")
    public ResponseEntity<ToDoDTO> completeToDoById(@PathVariable("id") Integer id) {
        ToDoDTO toDoDTO = this.toDoService.completeToDo(id);
        if (toDoDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(toDoDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @DeleteMapping(value = "/completed-todo")
    public ResponseEntity<String> deleteCompletedToDo() {
        this.toDoService.deleteCompletedToDo();
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}