package com.example.todo_list.data.dao;

import com.example.todo_list.data.entity.ToDo;
import com.example.todo_list.data.repository.ToDORepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoDAO {
    private final ToDORepository todoRepository;

    public List<ToDo> getAll() {
        return this.todoRepository.findAll();
    }

    public ToDo saveToDo(String title) {
        ToDo todo = ToDo.builder()
                .title(title)
                .completed(false)
                .created(LocalDateTime.now())
                .description("생성")
                .build();
        ToDo saveTodo = this.todoRepository.save(todo);
        return saveTodo;
    }

    public ToDo completeToDo(Integer id) {
        Optional<ToDo> updatedToDoOptional = this.todoRepository.findById(id);
        if (updatedToDoOptional.isPresent()) {
            ToDo toDo = updatedToDoOptional.get();
            toDo.setCompleted(true);
            return this.todoRepository.save(toDo);
        }
        return null;
    }

    public void deleteToDo(Integer id) {
        if (this.todoRepository.existsById(id)) {
            this.todoRepository.deleteById(id);
        }
    }

}
