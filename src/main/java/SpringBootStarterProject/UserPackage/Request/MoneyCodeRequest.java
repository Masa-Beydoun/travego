package SpringBootStarterProject.UserPackage.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class MoneyCodeRequest {

    private String code;

    public MoneyCodeRequest() {
    }
}
