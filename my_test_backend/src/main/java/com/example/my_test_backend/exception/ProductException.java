package com.example.my_test_backend.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ProductException {
    // ✅ [1] 유효성 검증 실패 (DTO 필드에 @Valid 적용 시)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // 어떤 필드가 어떤 이유로 실패했는지 메시지를 모음
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // ✅ [2] JSON 파싱 실패 (요청 본문이 잘못된 형식일 때)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("본문 오류: " + ex.getMessage());
    }

    // ✅ [3] 파라미터 타입 오류 (예: id에 문자열을 보낼 때 int로 못 바꾸는 오류)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = String.format("파라미터 오류: value:'%s'. Expected type:'%s'",
                ex.getValue(),
                ex.getRequiredType().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    // ✅ [4] DB에서 엔티티를 찾을 수 없을 때
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DB 검색 오류: " + ex.getMessage());
    }

    // ✅ [5] 사용자 정의 예외 (MyException 클래스 사용 시)
    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<String> handleMyException(MyException ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 정의 예외." + ex.getMessage());
    }

}
