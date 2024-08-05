package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.CommentPackage.Models.Comment;
import SpringBootStarterProject.HotelsPackage.Models.*;
import SpringBootStarterProject.ResourcesPackage.Model.FileMetaData;
import SpringBootStarterProject.ResourcesPackage.Response.FileMetaDataResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDetailsResponse {
    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer priceForExtraBed;
    private Double distanceFromCity;
    private Double breakfastPrice;
    private HotelResponse hotel;
    private List<RoomResponse> room;
    private List<Comment> commentReviews;
    private List<HotelServicesResponse> hotelServices;
    private List<FileMetaDataResponse> photo;
    private Double security;
    private Double location;
    private Double facilities;
    private Double cleanliness;
    private Double averageRating;

}
