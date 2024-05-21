package SpringBootStarterProject.HotelsPackage.HotelCommentReview;

import SpringBootStarterProject.HotelsPackage.Hotel.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelCommentReview {

    @Id
    @GeneratedValue
    private Integer id;
    private String comment;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Client client;
    private LocalDateTime createdAt;


}
