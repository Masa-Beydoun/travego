package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.RoomServices;
import jakarta.persistence.*;
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
    private Integer space;
    @NonNull
    private Integer maxNumOfPeople;
    @NonNull
    private Integer price;
    private List<String> roomServices;
}
