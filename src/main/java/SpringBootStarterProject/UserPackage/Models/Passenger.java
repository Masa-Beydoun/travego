package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passenger
{
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    private String first_name;

    private String last_name;

    private String father_name;

    private String mother_name;

    private String gender;

    private LocalDate birthdate;

    private String uniqueName;

//
//    @OneToOne(mappedBy = "passengerDetails")
//   private Passport passportId;
//
//    @OneToOne(mappedBy = "passengerDetails")
//    private Personalidenty  personalIdentyId;

   // @OneToOne(mappedBy = "passengerDetails")
  //  private Visa visaId;
}
