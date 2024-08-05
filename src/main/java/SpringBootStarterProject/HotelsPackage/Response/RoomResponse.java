package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.RoomServices;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoomResponse {
    private Integer id;
    private Integer num_of_bed;
    private Double space;
    private Integer maxNumOfPeople;
    private Double price;
    private List<RoomServices> roomServices;
    private String type;
    private Integer totalNumberOfRooms;

}
