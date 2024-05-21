package SpringBootStarterProject.HotelsPackage.Request;


import SpringBootStarterProject.HotelsPackage.HotelPackage.Hotel;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

@Builder
@Data
public class HotelDetailsRequest {

    @NotBlank(message = "start Time is null")
    private LocalTime startTime;

    @NotBlank(message = "end Time is null")
    private LocalTime endTime;

    @NotBlank(message = "prices for extra bed is null")
    private Integer priceForExtraBed;

    @NotBlank(message = "distance from city is null")
    private Double distanceFromCity;

    @NotBlank(message = "breakfast price is null")
    private Double breakfastPrice;

    @NotBlank(message = "hotel is null")
    private Hotel hotel;

    List<MultipartFile> photos;

}
