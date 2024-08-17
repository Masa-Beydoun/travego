package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Wallet wallet;

    private Integer transactionAmmount ;

    private LocalDate date;


    private int ClientId;

    @Enumerated(EnumType.STRING)
    private ServiceType type;

    private String description;

    private TransactionStatus status;

    private String tripName;
}
