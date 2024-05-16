package SpringBootStarterProject.UserPackage.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "clientId")
    private Client client;

    private int balance;

    @OneToMany
    private List<TransactionHistory> transactionHistory;

    private int securityCode;

    private String bankAccount;

}
