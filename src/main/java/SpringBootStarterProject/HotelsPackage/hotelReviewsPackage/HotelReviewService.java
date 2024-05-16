package SpringBootStarterProject.HotelsPackage.hotelReviewsPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelReviewService {

    @Autowired
    private HotelReviewRepository hotelReviewRepository;

    public List<HotelReview> findHotelReviewsById(Integer id) {
        return hotelReviewRepository.findByHotelId(id);
    }

    public HotelReview save(NewHotelReviewRequest newHotelReviewRequest) {
        HotelReview newHotelReview = new HotelReview(newHotelReviewRequest);
        return hotelReviewRepository.save(newHotelReview);
    }

    public void delete(HotelReview hotelReview) {
        hotelReviewRepository.delete(hotelReview);
    }





}
