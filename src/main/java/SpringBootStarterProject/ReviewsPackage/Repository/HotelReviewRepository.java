package SpringBootStarterProject.ReviewsPackage.Repository;

import SpringBootStarterProject.ReviewsPackage.Models.HotelReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelReviewRepository extends JpaRepository<HotelReview, Integer> {

    public List<HotelReview> findByHotelDetailsId(Integer hotelId);
    public Optional<HotelReview> findByClientIdAndHotelDetailsId(Integer clientId, Integer hotelDetailsId);



}
