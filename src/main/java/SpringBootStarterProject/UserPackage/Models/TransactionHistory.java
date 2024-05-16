package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.*;

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

    private int transactionAmmount ;

    private Date date;

   // hotel id

   //  trip id

    private String description;

    private TransactionStatus status;
}
