package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personalidenty {
    @Id
    @GeneratedValue
    private int id;

//    @OneToOne
//    @JoinColumn(name = "clientDetailsID")
//    private ClientDetails clientDetails;
//
//
//    @OneToOne
//    @JoinColumn(name = "passengerDetailsID")
//    private Passenger passengerDetails;

    private int relationshipId;

    @Enumerated(EnumType.STRING)
    private RelationshipType type;


    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String nationality;
    private String  getPersonalIdentity_PHOTO;

}
