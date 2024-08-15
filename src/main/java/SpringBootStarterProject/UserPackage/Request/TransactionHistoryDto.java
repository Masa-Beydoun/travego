package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.UserPackage.Models.ServiceType;
import SpringBootStarterProject.UserPackage.Models.TransactionHistory;
import SpringBootStarterProject.UserPackage.Models.TransactionStatus;
import SpringBootStarterProject.UserPackage.Models.Wallet;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link TransactionHistory}
 */
@Data
@Builder
public class TransactionHistoryDto implements Serializable {

    @NotNull(message = "transactionAmmount should be null")
    public Integer transactionAmmount;

    @NotNull(message = "date should be null")
    public LocalDate date;

    public int relationshipId;
    @NotNull(message = "type should be null")

    public  ServiceType type;

    @NotEmpty(message = "description shouldnt be Empty")
    public  String description;

    @NotNull(message = "Status shouldnt be Empty")
    public TransactionStatus status;
}