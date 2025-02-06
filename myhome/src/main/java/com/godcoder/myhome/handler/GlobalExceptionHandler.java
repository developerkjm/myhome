package com.godcoder.myhome.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        System.out.println("핸들러에서 나ㅡㄴ 오류 !!!  확인해 보셈!!! ");
        return ResponseEntity.badRequest().body("Validation failed: " + ex.getBindingResult().toString());
    }
}
