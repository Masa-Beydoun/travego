package SpringBootStarterProject.Trip_package.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class PaginationRequest {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;

    @Min(value = 1, message = "Page number should be greater than or equal to 1")
    private Integer page = DEFAULT_PAGE;

    @Min(value = 1, message = "Page size should be greater than or equal to 1")
    @Max(value = MAX_SIZE, message = "Page size should be less than or equal to " + MAX_SIZE + ".")
    private Integer size = DEFAULT_SIZE;
}