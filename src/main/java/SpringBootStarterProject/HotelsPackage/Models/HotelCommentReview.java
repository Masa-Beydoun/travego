package SpringBootStarterProject.HotelsPackage.Models;

import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.*;
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
    @SequenceGenerator(
            name = "hotel_comment_review_id",
            sequenceName = "hotel_comment_review_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hotel_comment_review_id"
    )
    private Integer id;
    private String comment;
    @ManyToOne(cascade = CascadeType.ALL)
    private Hotel hotel;
    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;

    private LocalDateTime createdAt;


}
