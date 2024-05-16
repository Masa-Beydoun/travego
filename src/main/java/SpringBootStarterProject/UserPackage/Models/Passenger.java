package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private Client clientId;


    private String first_name;

    private String last_name;


    private String gender;

    private LocalDate birthdate;


    @OneToOne(mappedBy = "passengerDetails")
   private Passport passportId;

    @OneToOne(mappedBy = "passengerDetails")
    private Personalidenty  personalIdentyId;

   // @OneToOne(mappedBy = "passengerDetails")
  //  private Visa visaId;
}
