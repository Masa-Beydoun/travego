package SpringBootStarterProject.HotelsPackage.HotelCommentReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelCommentReviewService {
    @Autowired
    private HotelCommentReviewRepository hotelCommentReviewRepository;

    public HotelCommentReview addComment(HotelCommentReview hotelCommentReview){
        return hotelCommentReviewRepository.save(hotelCommentReview);
    }

    public void deleteComment(HotelCommentReview hotelCommentReview){
        hotelCommentReviewRepository.delete(hotelCommentReview);
    }


    public List<HotelCommentReview> getHotelCommentReviewByHotelId(Integer id) {
        return hotelCommentReviewRepository.findByHotelId(id);
    }
}
