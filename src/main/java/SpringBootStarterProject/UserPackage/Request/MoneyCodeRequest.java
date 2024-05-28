package SpringBootStarterProject.UserPackage.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class MoneyCodeRequest {

    private String code;
    private String nothing;
}
