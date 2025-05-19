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

    @PutMapping(value = "/todo-list")
    public ResponseEntity<String> completeToDoById(@RequestBody ToDoDTO toDoDTO) {
        boolean success = this.toDoService.completeToDoById(toDoDTO.getId());
        if (success) {
            return ResponseEntity.ok("To-Do completed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("To-Do not completed");
        }
    }


    @DeleteMapping(value = "/todo-list")
    public ResponseEntity<String> deleteTodo() {
        boolean deleted = this.toDoService.deleteIfCompletedById();
        if (deleted) {
            return ResponseEntity.ok().body("Deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
    }

}
