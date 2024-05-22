package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter

public class HotelReviewRequest {
    private Integer id;

    private Hotel hotel;

    private LocalDate reviewDate;
    private Integer security;

    private Integer location;

    private Integer facilities;

    private Integer cleanliness;

    private Integer averageRating;



    private Client client;



}
