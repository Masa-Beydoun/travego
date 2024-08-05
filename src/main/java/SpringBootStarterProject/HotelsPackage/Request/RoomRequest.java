package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.HotelsPackage.Enum.RoomType;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.RoomServices;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {

    @NotNull(message =  "hotel details Id mist not be null")
    private Integer hotelDetailsId;
    @NotNull(message = "number of beds must not be null")
    private Integer num_of_bed;
    @NotNull(message =  "space must not be null")
    private Double space;
    @NotNull(message =  "max number of people must not be null")
    private Integer maxNumOfPeople;
    @NotNull( message =  "price must not be null")
    private Double price;

    private List<String> roomServices;
    @NotNull(message =  "room type must not be null")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @NotNull(message =  "total number of rooms must not be null")
    private Integer totalNumberOfRooms;

}
