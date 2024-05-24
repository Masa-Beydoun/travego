package SpringBootStarterProject.ManagingPackage.Response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Getter

public class ApiTest {
    private  final String message;
    private  final HttpStatus status;
    private  final LocalDateTime localDateTime;
    private  final Map<String,Object> body;

}
