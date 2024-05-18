package SpringBootStarterProject.ManagingPackage.ExceptionHandler;

import SpringBootStarterProject.ManagingPackage.exception.ObjectNotValidException;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ManagingPackage.exception.TooManyRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.LocalDateTime;

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


    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<String> TooManyRequestException(TooManyRequestException  ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> HttpServerErrorException(HttpServerErrorException  ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> AuthenticationServiceException(BadCredentialsException  ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("SOMTHING WENT WRONG");
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
@ExceptionHandler(value =RequestNotValidException.class)
    public ResponseEntity<Object> handleRequestNotValidException(RequestNotValidException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
            ex.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException,status);
    }
}
