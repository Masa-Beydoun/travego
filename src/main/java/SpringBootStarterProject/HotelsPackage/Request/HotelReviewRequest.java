package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Data
public class HotelReviewRequest {

    @NotNull
    private Integer hotelId;
    @Min(0)
    @Max(10)
    private Integer security;
    @Min(0)
    @Max(10)
    private Integer location;
    @Min(0)
    @Max(10)
    private Integer facilities;
    @Min(0)
    @Max(10)
    private Integer cleanliness;
    @NotNull
    private Integer clientId;



}
