package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.HotelsPackage.Enum.RoomType;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.RoomServices;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
@Builder
@Data
public class RoomRequest {

    @NonNull
    private Integer hotelDetailsId;
    @NonNull
    private Integer num_of_bed;
    @NonNull
    private Double space;
    @NonNull
    private Integer maxNumOfPeople;
    @NonNull
    private Double price;
    private List<String> roomServices;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
}
