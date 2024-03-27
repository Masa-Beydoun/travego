package com.example.security.ExceptionHandler;

import com.example.security.exception.ObjectNotValidException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterRule;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    /*
    @ResponseStatus(HttpStatus.BAD_REQUEST)

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {

                    if (errors.containsKey(error.getField())) {

                        errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));

                    } else {

                        errors.put(error.getField(), error.getDefaultMessage());

                    }

                }

        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());

    }

     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("The email already exists.");
    }
    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handlevalidationException(ObjectNotValidException v)
    {

        return ResponseEntity.badRequest().body(v.getErrormessage());
    }
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<String> handleMissingPartException(MissingServletRequestPartException ex) {
        if (ex.getRequestPartName().equals("video")) {
            // If the missing part is 'video', you can log a message or handle it accordingly
            System.out.println("Video part is missing in the request.");
            // You can continue with your code execution here
            return ResponseEntity.ok("Video part is missing in the request.");
        }
        // If it's not the 'video' part, rethrow the exception or handle it accordingly
        // For example, you can return a response indicating a bad request
        return ResponseEntity.badRequest().body("Required part '" + ex.getRequestPartName() + "' is not present.");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex)
    {
        //   ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex)
    {
        //   ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }
}
