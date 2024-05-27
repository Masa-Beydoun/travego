package SpringBootStarterProject.HotelsPackage.Repository;

import SpringBootStarterProject.HotelsPackage.Models.HotelCommentReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelCommentReviewRepository extends JpaRepository<HotelCommentReview, Integer> {

    public List<HotelCommentReview> findByHotelDetailsId(Integer hotelId);

    
}
