package SpringBootStarterProject.HotelsPackage.Repository;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface HotelDetailsRepository extends JpaRepository<HotelDetails, Integer> {

    public Optional<HotelDetails> findByHotelId(Integer hotelId);

//    public List<HotelDetails> findByAverageRatingAfterAndAverageRatingBefore(Double averageRatingAfter, Double averageRatingBefore);
    public Optional<List<HotelDetails>> findHotelDetailsByAverageRatingBetween(Double averageRatingAfter, Double averageRatingBefore);



}
