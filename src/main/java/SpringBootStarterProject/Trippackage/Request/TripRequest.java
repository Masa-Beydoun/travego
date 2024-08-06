package SpringBootStarterProject.Trippackage.Request;

import SpringBootStarterProject.ManagingPackage.annotation.ValidEnum;
import SpringBootStarterProject.Trippackage.Enum.FlightCompany;
import SpringBootStarterProject.Trippackage.Enum.TripCategory;
import SpringBootStarterProject.Trippackage.Enum.TripStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Builder
public class TripRequest {
    @NotBlank(message = "name must not be empty")
    private String tripName;
    @NotBlank(message = "Description must not be empty")
    private String tripDescription;

    @NotBlank(message = "Category must not be empty")
    @ValidEnum(enumClass = TripCategory.class , message = "value must be value of TripCategory class")
    private String tripCategory;
//    @NotBlank(message = "start date must not be empty")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")

    private LocalDateTime tripStartDate;
//    @NotBlank(message = "end date must not be empty")

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime tripEndDate;

    @NotNull(message = "country must not be empty")
    private Integer country;
//    @NotBlank(message = "cities must not be empty")
    @NotNull(message = "cities must not be empty")
    private List<Integer> cities;
//    @NotEmpty(message = "hotels must not be empty")
    private List<Integer> hotels;

    @NotEmpty(message = "flight company must not be empty")
    @ValidEnum(enumClass = FlightCompany.class , message = "value must be value of FlightCompany class")
    private String flightCompany;

//    @NotBlank(message = "min number of passengers must not be empty")
    private Integer min_passengers;
//    @NotBlank(message = "max number of passengers must not be empty")
    private Integer max_passengers;

    @NotEmpty(message = "state of trip must not be empty")
    @ValidEnum(enumClass = TripStatus.class , message = "value must be value of TripStatus class" )
    private String  status;

//    @NotEmpty(message = "services must not be empty")
    private List<String> tripServices;

//    @NotEmpty(")
//    @NotBlank(message = "price must not be empty")

//    @NotNull(message = "hotel-price must not be null" )
    private Optional<Integer> hotelPrice;
    @NotNull(message = "flight-price must not be null")
    private Integer flightPrice;
    @NotNull(message = "service-price must not be null")
    private Integer servicesPrice;
    private Boolean isPrivate;


}
