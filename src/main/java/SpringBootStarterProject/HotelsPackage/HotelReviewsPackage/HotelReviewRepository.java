package SpringBootStarterProject.HotelsPackage.HotelReviewsPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelReviewRepository extends JpaRepository<HotelReview, Integer> {

    public List<HotelReview> findByHotelId(Integer hotelId);


}
