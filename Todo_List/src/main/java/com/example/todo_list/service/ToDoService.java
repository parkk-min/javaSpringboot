package com.example.todo_list.service;

import com.example.todo_list.data.dao.ToDoDAO;
import com.example.todo_list.data.dto.ToDoDTO;
import com.example.todo_list.data.entity.ToDo;
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
                    .build();
            toDoDTOList.add(toDoDTO);
        }
        return toDoDTOList;
    }

    public ToDoDTO saveToDo(ToDoDTO toDoDTO) {
        // DAO를 호출해서 데이터를 저장하고 저장된 결과를 ToDo 엔티티 객체로 받아와서 'todo' 변수에 저장
        ToDo todo = this.toDoDAO.saveToDo(toDoDTO.getTitle(),
                LocalDateTime.now(), "생성");
        // 'todo' 변수 (ToDo 엔티티 객체)에 담긴 정보를 가지고 새로운 ToDoDTO 객체를 만듦
        ToDoDTO saveToDoDTO = ToDoDTO.builder()
                .id(todo.getId()) // 'todo' (엔티티)에서 getId()로 ID를 가져와 DTO에 설정
                .title(todo.getTitle()) // 'todo' (엔티티)에서 getTitle()로 제목을 가져와 DTO에 설정
                .build();
        return saveToDoDTO;
    }

    public boolean completeToDoById(Integer id) {
        ToDo todo = this.toDoDAO.completeToDoById(id);
        return todo != null;
    }

    public boolean deleteIfCompletedById() {
        List<ToDo> todolist = this.toDoDAO.getAll();

        for (ToDo todo : todolist) {
            if (todo.isCompleted()) {
                this.toDoDAO.deleteIfCompletedById(todo.getId());
            }
        }
        return true;
    }

}
