package SpringBootStarterProject.HotelsPackage.HotelCommentreview;

import SpringBootStarterProject.HotelsPackage.hotelPackage.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Models.ClientDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class HotelCommentReview {

    @Id
    @GeneratedValue
    private Integer id;
    private String comment;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Client client;


}
