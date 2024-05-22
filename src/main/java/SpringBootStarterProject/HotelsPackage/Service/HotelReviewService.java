package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Request.HotelReviewRequest;
import SpringBootStarterProject.HotelsPackage.Models.HotelReview;
import SpringBootStarterProject.HotelsPackage.Repository.HotelReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HotelReviewService {

    @Autowired
    private HotelReviewRepository hotelReviewRepository;

    public List<HotelReview> findHotelReviewsById(Integer id) {
        return hotelReviewRepository.findByHotelId(id);
    }

    public HotelReview save(HotelReviewRequest hotelReviewRequest) {
        double avg = hotelReviewRequest.getCleanliness() + hotelReviewRequest.getSecurity() + hotelReviewRequest.getLocation() + hotelReviewRequest.getFacilities() / 4.0;

        HotelReview newHotelReview = HotelReview.builder()
                .hotel(hotelReviewRequest.getHotel())
                .averageRating(avg)
                .cleanliness(hotelReviewRequest.getCleanliness())
                .facilities(hotelReviewRequest.getFacilities())
                .location(hotelReviewRequest.getLocation())
                .security(hotelReviewRequest.getSecurity())
                .reviewDate(LocalDate.now())
                .build();
        return hotelReviewRepository.save(newHotelReview);
    }

    public void delete(HotelReview hotelReview) {
        hotelReviewRepository.delete(hotelReview);
    }





}
