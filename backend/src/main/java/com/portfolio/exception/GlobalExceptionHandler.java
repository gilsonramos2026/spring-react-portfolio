package com.portfolio.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import java.time.LocalDateTime;
import java.util.*;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {
    record Err(int status, String error, Object message, LocalDateTime timestamp){}

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Err> notFound(ResourceNotFoundException e){
        return ResponseEntity.status(404).body(new Err(404,"Not Found",e.getMessage(),LocalDateTime.now()));
    }
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<Err> fileStorage(FileStorageException e){
        return ResponseEntity.badRequest().body(new Err(400,"File Error",e.getMessage(),LocalDateTime.now()));
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Err> maxSize(MaxUploadSizeExceededException e){
        return ResponseEntity.status(413).body(new Err(413,"Too Large","Arquivo excede 5MB",LocalDateTime.now()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Err> validation(MethodArgumentNotValidException e){
        Map<String,String> errs=new LinkedHashMap<>();
        e.getBindingResult().getAllErrors().forEach(err->{
            String f=err instanceof FieldError fe?fe.getField():err.getObjectName();
            errs.put(f,err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(new Err(400,"Validation Failed",errs,LocalDateTime.now()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Err> general(Exception e){
        return ResponseEntity.status(500).body(new Err(500,"Internal Error",e.getMessage(),LocalDateTime.now()));
    }
}
