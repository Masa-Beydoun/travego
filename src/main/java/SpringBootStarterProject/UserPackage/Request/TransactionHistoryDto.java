package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.UserPackage.Models.ServiceType;
import SpringBootStarterProject.UserPackage.Models.TransactionHistory;
import SpringBootStarterProject.UserPackage.Models.TransactionStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link TransactionHistory}
 */
@Value
public class TransactionHistoryDto implements Serializable {
    @NotNull(message = "transactionAmmount should be null")
    Integer transactionAmmount;
    @NotNull(message = "date should be null")
    LocalDate date;
    int relationshipId;
    @NotNull(message = "type should be null")
    ServiceType type;
    @NotEmpty(message = "description shouldnt be Empty")
    String description;
    @NotNull(message = "Status shouldnt be Empty")
    TransactionStatus status;
}