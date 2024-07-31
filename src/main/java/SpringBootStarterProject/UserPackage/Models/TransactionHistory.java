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
public class TransactionHistory {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Wallet wallet;

    private Integer transactionAmmount ;

    private LocalDate date;


    private int relationshipId;

    @Enumerated(EnumType.STRING)
    private ServiceType type;

    private String description;

    private TransactionStatus status;
}
