package com.portfolio.exception;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestController
public class ExceptionTestController {

    @GetMapping("/api/test-error/missing")
    public void throwNotFound() {
        throw new ResourceNotFoundException("Registro ausente no banco de dados");
    }

    @GetMapping("/api/test-error/storage")
    public void throwFileStorage() {
        throw new FileStorageException("Extensão não permitida");
    }

    @GetMapping("/api/test-error/large")
    public void throwMaxUpload() {
        throw new MaxUploadSizeExceededException(5242880L);
    }

    @GetMapping("/api/test-error/generic")
    public void throwGeneric() {
        throw new RuntimeException("Falha crítica interna");
    }

    @GetMapping("/api/test-error/validation")
    public void throwValidation() throws MethodArgumentNotValidException {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "requestDto");
        bindingResult.addError(new FieldError("requestDto", "email", "O e-mail digitado é inválido"));
        throw new MethodArgumentNotValidException(null, bindingResult);
    }
}
