package com.test.profile.service.ProfileService.controller;

import com.test.profile.service.ProfileService.model.ImageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ImageNotFoundException> handleConflict(ImageNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
    }
}
