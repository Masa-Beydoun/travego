package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
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

    @OneToOne(mappedBy = "clientDetails", cascade = CascadeType.ALL)
    private Client client;

    private String gender;

    private Date birthdate;


 // private Country country;

//    @OneToOne(mappedBy = "clientDetails")
//    private Passport passportId;
//
//    @OneToOne(mappedBy = "clientDetails")
//    private Personalidenty  personalIdentyId;

    //@OneToOne(mappedBy = "clientDetails")
  //  private Visa visaId;
}
