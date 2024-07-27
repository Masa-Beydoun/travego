package SpringBootStarterProject.HotelReservationPackage.Model;

import SpringBootStarterProject.UserPackage.Models.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
@Getter
public class HotelReservationPassengerDetails {

    @Id
    @GeneratedValue
    private Integer id;
//    Todo: : Add relation in Client for this
    // @ManyToOne
    private Integer clientId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private HotelReservation hotelReservation;

    @OneToOne(cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonIgnore
    private HotelConfirmationPassengersDetails hotelConfirmationPassengersDetails;

    private String firstName;

    private String lastname;

    private String fatherName;

    private String motherName;

    private LocalDate birthdate;


    private String nationality;

    private String personalIdentity_PHOTO;

    private LocalDate passport_issue_date;

    private LocalDate passport_expires_date;

    private String passport_number;

    private String passport_PHOTO;

    private String visa_Type;

    private String visa_Country;

    private LocalDate visa_issue_date;

    private LocalDate visa_expires_date;

    private String visa_PHOTO;



}
