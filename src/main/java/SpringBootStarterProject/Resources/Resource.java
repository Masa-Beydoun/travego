package SpringBootStarterProject.Resources;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue
    private Integer id;
    @Enumerated
    private ResourceType resourceType;
    private Integer relationId;

}
