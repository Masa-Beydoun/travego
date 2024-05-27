package SpringBootStarterProject.HotelsPackage.Repository;

import SpringBootStarterProject.HotelsPackage.Models.HotelReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelReviewRepository extends JpaRepository<HotelReview, Integer> {

    public List<HotelReview> findByHotelDetailsId(Integer hotelId);
    public HotelReview findByClientIdAndHotelDetailsId(Integer clientId, Integer hotelDetailsId);



}
