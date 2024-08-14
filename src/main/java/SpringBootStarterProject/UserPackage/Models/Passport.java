package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passport {
    @Id
    @GeneratedValue
    private Integer id;

//    @OneToOne
//    @JoinColumn(name = "clientDetailsID")
//    private ClientDetails clientDetails;
//
//    @OneToOne
//    @JoinColumn(name = "passengerDetailsID")
//    private Passenger passengerDetails;

    private int relationshipId;

    @Enumerated(EnumType.STRING)
    private RelationshipType type;

    private String firstName;
    private String lastName;
    private LocalDate passport_issue_date;
    private LocalDate passport_expires_date;
    private String passport_number;
    private String passport_PHOTO;


}
