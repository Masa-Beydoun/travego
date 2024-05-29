package SpringBootStarterProject.HotelsPackage.Repository;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HotelDetailsRepository extends JpaRepository<HotelDetails, Integer> {

    public HotelDetails findByHotelId(Integer hotelId);

//    public List<HotelDetails> findByAverageRatingAfterAndAverageRatingBefore(Double averageRatingAfter, Double averageRatingBefore);
    public List<HotelDetails> findHotelDetailsByAverageRatingBetween(Double averageRatingAfter, Double averageRatingBefore);

}
