package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.Repository.HotelCommentReviewRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelCommentReviewRequest;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor()
public class HotelCommentReviewService {
    @Autowired
    private final HotelCommentReviewRepository hotelCommentReviewRepository;
    @Autowired
    private final ObjectsValidator<HotelCommentReviewRequest> validator;
    @Autowired
    private final ClientRepository clientRepository;

    public HotelCommentReview addComment(HotelCommentReviewRequest request){

        validator.validate(request);
        Client client =clientRepository.findByEmail(request.getClient().getEmail()).orElseThrow(()-> new RuntimeException("Client is not found"));

        HotelCommentReview hotelCommentReview = HotelCommentReview.builder()
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .client(client)
                .build();

        //TODO : later when there is reservation .. check the ability for adding a comment
        return hotelCommentReviewRepository.save(hotelCommentReview);
    }

    public void deleteComment(HotelCommentReview hotelCommentReview){
        hotelCommentReviewRepository.delete(hotelCommentReview);
    }


    public List<HotelCommentReview> getHotelCommentReviewByHotelId(Integer id) {
        return hotelCommentReviewRepository.findByHotelId(id);
    }
}
