package SpringBootStarterProject.HotelsPackage.Request;


import SpringBootStarterProject.HotelsPackage.Enum.HotelServiceType;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.Models.HotelReview;
import SpringBootStarterProject.HotelsPackage.Models.Room;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
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

    @NonNull
    private Integer priceForExtraBed;

    @NotNull
    private Double distanceFromCity;

    @NotNull
    private Double breakfastPrice;

    @NotBlank(message = "hotel is null")
    private Integer hotelId;

    private List<Integer> roomsId;
    private List<String> hotelServices;
    private List<Integer> commentReviewsId;
    private List<Integer> hotelReviews;

//    List<MultipartFile> photos;

}
