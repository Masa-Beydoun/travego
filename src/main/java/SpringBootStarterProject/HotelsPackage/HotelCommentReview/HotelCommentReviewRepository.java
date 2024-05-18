package SpringBootStarterProject.HotelsPackage.HotelCommentReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelCommentReviewRepository extends JpaRepository<HotelCommentReview, Integer> {

    public List<HotelCommentReview> findByHotelId(Integer hotelId);

    
}
