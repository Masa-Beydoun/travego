package SpringBootStarterProject.HotelsPackage.Request;


import SpringBootStarterProject.HotelsPackage.Enum.HotelServiceType;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.Models.HotelReview;
import SpringBootStarterProject.HotelsPackage.Models.Room;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
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

    private List<MultipartFile> photos;

}
