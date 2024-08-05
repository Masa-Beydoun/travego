package SpringBootStarterProject.HotelsPackage.Request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private List<MultipartFile> photos;


    @Builder.Default
    private Double security=5.0;
    @Builder.Default
    private Double location=5.0;
    @Builder.Default
    private Double facilities=5.0;
    @Builder.Default
    private Double cleanliness=5.0;
    @Builder.Default
    private Double averageRating=5.0;

}
