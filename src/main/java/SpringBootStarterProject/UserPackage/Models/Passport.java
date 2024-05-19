package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated
    private RelationshipType type;

    private String UNTIL_ABOOD_TILL_;

}
