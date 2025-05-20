package com.example.todo_list.service;

import com.example.todo_list.data.dao.ToDoDAO;
import com.example.todo_list.data.dto.ToDoDTO;
import com.example.todo_list.data.entity.ToDo;
import com.example.todo_list.exception.MyException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoDAO toDoDAO;

    public List<ToDoDTO> getAll() {
        List<ToDoDTO> toDoDTOList = new ArrayList<>();
        List<ToDo> todolist = this.toDoDAO.getAll();
        for (ToDo toDo : todolist) {
            ToDoDTO toDoDTO = ToDoDTO.builder()
                    .id(toDo.getId())
                    .title(toDo.getTitle())
                    .completed(toDo.isCompleted())
                    .build();
            toDoDTOList.add(toDoDTO);
        }
        return toDoDTOList;
    }

    public ToDoDTO saveToDo(ToDoDTO toDoDTO) {
        if (toDoDTO.getTitle().contains("놀기")) {
            throw new MyException("놀기를 TODO에 추가 할 수 없습니다.");
        }
        ToDo todo = this.toDoDAO.saveToDo(toDoDTO.getTitle());
        ToDoDTO saveToDoDTO = ToDoDTO.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .build();
        return saveToDoDTO;
    }

    public ToDoDTO completeToDo(Integer id) {
        ToDo todo = this.toDoDAO.completeToDo(id);
        if (todo != null) {
            ToDoDTO toDoDTO = ToDoDTO.builder()
                    .id(todo.getId())
                    .title(todo.getTitle())
                    .completed(true)
                    .build();
            return toDoDTO;
        }
        return null;
    }

    public void deleteCompletedToDo() {
        List<ToDo> todolist = this.toDoDAO.getAll();
        for (ToDo todo : todolist) {
            if (todo.isCompleted()) {
                this.toDoDAO.deleteToDo(todo.getId());
            }
        }
    }


}