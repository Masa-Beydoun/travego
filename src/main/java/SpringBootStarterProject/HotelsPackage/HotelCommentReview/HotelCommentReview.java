package SpringBootStarterProject.HotelsPackage.HotelCommentReview;

import SpringBootStarterProject.HotelsPackage.hotelPackage.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
