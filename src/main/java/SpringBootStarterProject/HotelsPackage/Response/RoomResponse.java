package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.RoomServices;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RoomResponse {
    private Integer id;
    private HotelDetails hotelDetails;
    private Integer num_of_bed;
    private Integer space;
    private Integer maxNumOfPeople;
    private Integer price;
    private List<RoomServices> roomServices;
    private String type;
}
