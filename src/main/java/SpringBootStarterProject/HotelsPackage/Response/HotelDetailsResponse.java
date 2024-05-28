package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.HotelsPackage.Models.*;
import SpringBootStarterProject.ResourcesPackage.FileEntity;
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
    private List<HotelCommentReview> commentReviews;
    private List<HotelServicesResponse> hotelServices;
    private List<FileEntity> photo;
    private Double security;
    private Double location;
    private Double facilities;
    private Double cleanliness;
    private Double averageRating;

}
