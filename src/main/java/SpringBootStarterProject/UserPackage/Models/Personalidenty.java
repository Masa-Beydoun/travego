package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personalidenty {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "clientDetailsID")
    private ClientDetails clientDetails;


    @OneToOne
    @JoinColumn(name = "passengerDetailsID")
    private Passenger passengerDetails;
}
