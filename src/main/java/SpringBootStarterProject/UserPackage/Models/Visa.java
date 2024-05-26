package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visa {
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

    private String visaType;
    private String country;
    private LocalDate issueDate;
    private LocalDate expiryDate;


}
