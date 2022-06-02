package com.company.controller;

import com.company.exeption.GlobalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<?> handleBadException(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
