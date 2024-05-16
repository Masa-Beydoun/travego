package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDetails {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Client clientId;

    private String gender;

    private LocalDate birthdate;

 // private Country country;

    @OneToOne(mappedBy = "clientDetails")
    private Passport passportId;

    @OneToOne(mappedBy = "clientDetails")
    private Personalidenty  personalIdentyId;

    //@OneToOne(mappedBy = "clientDetails")
  //  private Visa visaId;
}
