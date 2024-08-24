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

    @OneToOne
    @JoinColumn(name = "client_details_id")
    private Client client;

    private String gender;

    private String father_name;

    private String mother_name;

    private LocalDate birthdate;

    // TODO:: ADD PHOTO


 // private Country country;

//    @OneToOne(mappedBy = "clientDetails")
//    private Passport passportId;
//
//    @OneToOne(mappedBy = "clientDetails")
//    private Personalidenty  personalIdentyId;

    //@OneToOne(mappedBy = "clientDetails")
  //  private Visa visaId;
}
