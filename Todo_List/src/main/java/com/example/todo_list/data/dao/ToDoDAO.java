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

    public ToDo saveToDo(String title, LocalDateTime created, String description) {
        ToDo todo = ToDo.builder()
                .title(title)
                .created(created)
                .description(description)
                .build();
        return todoRepository.save(todo);
    }

    public ToDo completeToDoById(Integer id) {
        // 1. 해당 ID의 To-Do 항목을 데이터베이스에서 조회
        Optional<ToDo> todo = this.todoRepository.findById(id);

        // 2. To-Do 항목이 존재하는지 확인
        if (todo.isPresent()) { // 존재한다면
            // 3. Optional에서 To-Do 엔티티 객체를 꺼냄
            ToDo toDo = todo.get();

            // 4. 가져온 To-Do 객체의 completed 상태를 인자로 받은 값으로 설정
            toDo.setCompleted(true); // 예: true면 완료, false면 미완료로 설정

            // 5. 변경된 To-Do 객체를 데이터베이스에 저장(업데이트)하고, 업데이트된 객체를 반환
            return this.todoRepository.save(toDo); // save 메소드는 없으면 저장, 있으면 업데이트
        }
        return null;
    }

    public boolean deleteIfCompletedById(Integer id) {
        // 이 메소드가 호출되면 'id'라는 번호를 가진 To-Do 항목이 있는지 찾아본다
        Optional<ToDo> todo = this.todoRepository.findById(id); // 데이터베이스에서 id로 To-Do 찾기

        // 만약 그 'id' 번호를 가진 To-Do 항목을 찾았고 (todo.isPresent()가 true),
        // 그리고 그 항목의 상태가 '완료' (completed가 true) 이면
        if (todo.isPresent()) { // 일단 To-Do가 있는지 확인
            ToDo toDo = todo.get(); // 찾았으면 To-Do 객체 꺼내기

            if (toDo.isCompleted()) { // 이 To-Do가 완료 상태인지 확인
                // '완료 상태'가 맞다면
                // 이 To-Do 항목을 데이터베이스에서 삭제해달라고 요청할 거야
                this.todoRepository.deleteById(id); // 데이터베이스에서 해당 id의 To-Do 삭제

                // 그리고 삭제가 성공했으니까 이 메소드를 호출한 쪽에 '성공했어!'라고 알려줄 거야
                return true; // 삭제 성공
            }
        }
        return false; // 해당 ID의 To-Do를 찾지 못함
    }

}
