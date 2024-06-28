package SpringBootStarterProject.Trip_ReservationPackage.Request;

import SpringBootStarterProject.Trip_ReservationPackage.Models.TripReservation;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@Setter
@Getter
public class PassengerDetailsRequest {

    private Integer passenger_Id;

    private Integer tripReservation;

    @NotBlank(message = "fisrtname shouldnt be blank")
    private String fisrtname;
    @NotBlank(message = "lastname shouldnt be blank")
    private String lastname;
    @NotBlank(message = "fathername shouldnt be blank")
    private String fathername;
    @NotBlank(message = "mothername shouldnt be blank")
    private String mothername;
    @NotNull(message = "bitrhdate shouldnt be blank")
    private LocalDate bitrhdate;
    @NotBlank(message = "nationality shouldnt be blank")
    private String nationality;
    @NotBlank(message = "personalIdentity_PHOTO shouldnt be blank")
    private String personalIdentity_PHOTO;
    @NotNull(message = "passport_Issue_date shouldnt be blank")
    private LocalDate passport_Issue_date;
    @NotNull(message = "passport_Expires_date shouldnt be blank")
    private LocalDate passport_Expires_date;
    @NotBlank(message = "passport_Number shouldnt be blank")
    private String passport_Number;
    @NotBlank(message = "passport_PHOTO shouldnt be blank")
    private String passport_PHOTO;
    @NotBlank(message = "visa_Type shouldnt be blank")
    private String visa_Type;
    @NotBlank(message = "visa_Country shouldnt be blank")
    private String visa_Country;
    @NotNull(message = "visa_Issue_date shouldnt be blank")
    private LocalDate visa_Issue_date;
    @NotNull(message = "visa_Expires_date shouldnt be blank")
    private LocalDate visa_Expires_date;
    @NotBlank(message = "visa_PHOTO shouldnt be blank")
    private String visa_PHOTO;


    public PassengerDetailsRequest() {
    }

    public PassengerDetailsRequest(Integer passenger_Id, Integer tripReservation, String fisrtname, String lastname, String fathername, String mothername, LocalDate bitrhdate, String nationality, String personalIdentity_PHOTO, LocalDate passport_Issue_date, LocalDate passport_Expires_date, String passport_Number, String passport_PHOTO, String visa_Type, String visa_Country, LocalDate visa_Issue_date, LocalDate visa_Expires_date, String visa_PHOTO) {

        this.passenger_Id = passenger_Id;
        this.tripReservation = tripReservation;
        this.fisrtname = fisrtname;
        this.lastname = lastname;
        this.fathername = fathername;
        this.mothername = mothername;
        this.bitrhdate = bitrhdate;
        this.nationality = nationality;
        this.personalIdentity_PHOTO = personalIdentity_PHOTO;
        this.passport_Issue_date = passport_Issue_date;
        this.passport_Expires_date = passport_Expires_date;
        this.passport_Number = passport_Number;
        this.passport_PHOTO = passport_PHOTO;
        this.visa_Type = visa_Type;
        this.visa_Country = visa_Country;
        this.visa_Issue_date = visa_Issue_date;
        this.visa_Expires_date = visa_Expires_date;
        this.visa_PHOTO = visa_PHOTO;
    }


    public Integer getPassenger_Id() {
        return passenger_Id;
    }

    public void setPassenger_Id(Integer passenger_Id) {
        this.passenger_Id = passenger_Id;
    }

    public Integer getTripReservation() {
        return tripReservation;
    }

    public void setTripReservation(Integer tripReservation) {
        this.tripReservation = tripReservation;
    }

    public String getFisrtname() {
        return fisrtname;
    }

    public void setFisrtname(String fisrtname) {
        this.fisrtname = fisrtname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public LocalDate getBitrhdate() {
        return bitrhdate;
    }

    public void setBitrhdate(LocalDate bitrhdate) {
        this.bitrhdate = bitrhdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPersonalIdentity_PHOTO() {
        return personalIdentity_PHOTO;
    }

    public void setPersonalIdentity_PHOTO(String personalIdentity_PHOTO) {
        this.personalIdentity_PHOTO = personalIdentity_PHOTO;
    }

    public LocalDate getPassport_Issue_date() {
        return passport_Issue_date;
    }

    public void setPassport_Issue_date(LocalDate passport_Issue_date) {
        this.passport_Issue_date = passport_Issue_date;
    }

    public LocalDate getPassport_Expires_date() {
        return passport_Expires_date;
    }

    public void setPassport_Expires_date(LocalDate passport_Expires_date) {
        this.passport_Expires_date = passport_Expires_date;
    }

    public String getPassport_Number() {
        return passport_Number;
    }

    public void setPassport_Number(String passport_Number) {
        this.passport_Number = passport_Number;
    }

    public String getPassport_PHOTO() {
        return passport_PHOTO;
    }

    public void setPassport_PHOTO(String passport_PHOTO) {
        this.passport_PHOTO = passport_PHOTO;
    }

    public String getVisa_Type() {
        return visa_Type;
    }

    public void setVisa_Type(String visa_Type) {
        this.visa_Type = visa_Type;
    }

    public String getVisa_Country() {
        return visa_Country;
    }

    public void setVisa_Country(String visa_Country) {
        this.visa_Country = visa_Country;
    }

    public LocalDate getVisa_Issue_date() {
        return visa_Issue_date;
    }

    public void setVisa_Issue_date(LocalDate visa_Issue_date) {
        this.visa_Issue_date = visa_Issue_date;
    }

    public LocalDate getVisa_Expires_date() {
        return visa_Expires_date;
    }

    public void setVisa_Expires_date(LocalDate visa_Expires_date) {
        this.visa_Expires_date = visa_Expires_date;
    }

    public String getVisa_PHOTO() {
        return visa_PHOTO;
    }

    public void setVisa_PHOTO(String visa_PHOTO) {
        this.visa_PHOTO = visa_PHOTO;
    }


}
