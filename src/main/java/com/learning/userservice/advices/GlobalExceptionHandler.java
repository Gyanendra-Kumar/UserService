package com.learning.userservice.advices;

import com.learning.userservice.dtos.ExceptionDto;
import com.learning.userservice.exceptions.InvalidTokeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokeException.class)
    public ResponseEntity<ExceptionDto> handleInvalidToken(){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Unauthorized access, please try with correct email and password.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionDto);
    }
}
