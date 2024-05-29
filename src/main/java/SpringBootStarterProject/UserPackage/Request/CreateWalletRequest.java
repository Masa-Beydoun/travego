package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.ManagingPackage.annotation.ValidPassword;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Models.TransactionHistory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateWalletRequest {


    @ValidPassword
    private String securityCode;

    private String confiremSecurityCode;

    private String bankAccount;
}
