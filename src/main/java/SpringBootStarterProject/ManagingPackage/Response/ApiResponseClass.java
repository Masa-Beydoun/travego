package SpringBootStarterProject.ManagingPackage.Response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ApiResponseClass {
    private  final String message;
    private  final HttpStatus status;
    private  final LocalDateTime localDateTime;
    private  final Object body;

}
