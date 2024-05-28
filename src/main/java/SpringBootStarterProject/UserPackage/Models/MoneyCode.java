package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyCode {
    @Id
    @GeneratedValue
    private Integer id;

    private String code;

    private Integer balance;

    private boolean valid;


}
