package SpringBootStarterProject.ManagingPackage.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Add this line
public class ApiTest {

    private   String message;
    private   HttpStatus status;
    private   LocalDateTime localDateTime;
    private   Object body;
    private   Map<String,Object> data;
    public ApiTest(String message, HttpStatus status, LocalDateTime localDateTime) {
        this.message = message;
        this.status = status;
        this.localDateTime = localDateTime;
    }

    public ApiTest(String message, HttpStatus status, LocalDateTime localDateTime, Object body) {
        this.message = message;
        this.status = status;
        this.localDateTime = localDateTime;
        this.body = body;
    }
    public ApiTest(String message, HttpStatus status, LocalDateTime localDateTime, Map<String, Object> data) {
        this.message = message;
        this.status = status;
        this.localDateTime = localDateTime;
        this.data = data;
    }
}
