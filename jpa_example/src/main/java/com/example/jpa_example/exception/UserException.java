package com.example.jpa_example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserException {
    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<String> handleMyException(MyException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 정의 예외 " + e.getMessage());
    }

}
