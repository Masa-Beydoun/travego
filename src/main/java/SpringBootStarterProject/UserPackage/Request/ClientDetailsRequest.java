package SpringBootStarterProject.UserPackage.Request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class ClientDetailsRequest {


    private String gender;

    private Date birthDate;


    private String passportfirstName;
    private String passportlastName;
    private Date passportIssueDate;
    private Date passportExpiryDate;
    private String passportNumber;

    private String idfirstName;
    private String idlastName;
    private Date idBirthDate;
    private String nationality;



    private String visaType;
    private String country;
    private Date visaIssueDate;
    private Date visaExpiryDate;
}
