package SpringBootStarterProject.ManagingPackage.ExceptionHandler;

import SpringBootStarterProject.ManagingPackage.exception.EmailTakenException;
import SpringBootStarterProject.ManagingPackage.exception.ObjectNotValidException;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ManagingPackage.exception.TooManyRequestException;
import SpringBootStarterProject.UserPackage.Response.ApiRespnse;
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


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiRespnse> NullPointerException(NullPointerException  ex) {

        var response = new ApiRespnse(ex.getMessage(),HttpStatus.NO_CONTENT, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<ApiRespnse> EmailTakenException(EmailTakenException  ex) {

        var response = new ApiRespnse(ex.getMessage(),HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<ApiRespnse> TooManyRequestException(TooManyRequestException  ex) {

       var response = new ApiRespnse(ex.getMessage(),HttpStatus.TOO_MANY_REQUESTS, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> HttpServerErrorException(HttpServerErrorException  ex) {

        //var response = new ApiRespnse(ex.getMessage(),HttpStatus., LocalDateTime.now());

       // return ResponseEntity.status(response.getStatus()).body(response);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiRespnse> AuthenticationServiceException(BadCredentialsException  ex) {

        var response = new ApiRespnse(ex.getMessage(),HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
     //   return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiRespnse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        var response = new ApiRespnse(ex.getMessage(),HttpStatus.CONFLICT, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
      //  return ResponseEntity.status(HttpStatus.CONFLICT).body("SOMTHING WENT WRONG");
    }
    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<ApiRespnse> handlevalidationException(ObjectNotValidException ex)
    {
        var response = new ApiRespnse(ex.getErrormessage().toString(),HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
      //  return ResponseEntity.badRequest().body(v.getErrormessage());
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiRespnse> handleUsernameNotFoundException(UsernameNotFoundException ex)
    {

        var response = new ApiRespnse(ex.getMessage(),HttpStatus.NOT_FOUND, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
       // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiRespnse> handleIllegalStateException(IllegalStateException ex)
    {

        var response = new ApiRespnse(ex.getMessage(),HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);

      //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

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
