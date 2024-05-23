package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.Models.HotelReview;
import SpringBootStarterProject.HotelsPackage.Models.Room;
import SpringBootStarterProject.Trip_package.Models.HotelServices;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;
@Builder
@Data
public class HotelDetailsResponse {
    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer priceForExtraBed;
    private Double distanceFromCity;
    private Double breakfastPrice;
//    private List<Integer> photos;
    private Hotel hotel;
    private List<Room> room;
    private List<HotelReview> reviews;
    private List<HotelCommentReview> commentReviews;
    private List<HotelServices> hotelServices;

}
