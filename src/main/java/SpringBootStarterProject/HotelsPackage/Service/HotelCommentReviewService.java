package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.Repository.HotelCommentReviewRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelCommentReviewRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelCommentReviewResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private HotelRepository hotelRepository;

    public HotelCommentReviewResponse addComment(HotelCommentReviewRequest request){

        validator.validate(request);

        Client client =clientRepository.findById(request.getClientId()).orElseThrow(()-> new RuntimeException("Client is not found"));
        Hotel hotel= hotelRepository.findById(request.getHotelId()).orElseThrow(()-> new RuntimeException("Hotel is not found"));

        HotelCommentReview hotelCommentReview = HotelCommentReview.builder()
                .comment(request.getComment())
                .hotel(hotel)
                .createdAt(LocalDateTime.now())
                .client(client)
                .build();

        hotelCommentReviewRepository.save(hotelCommentReview);
        return HotelCommentReviewResponse.builder()
                .id(hotelCommentReview.getId())
                .hotel(hotelCommentReview.getHotel())
                .createdAt(hotelCommentReview.getCreatedAt())
                .client(hotelCommentReview.getClient())
                .comment(hotelCommentReview.getComment())
                .build();
        //TODO : later when there is reservation .. check the ability for adding a comment
    }

    public void deleteComment(Integer id){
        HotelCommentReview review = hotelCommentReviewRepository.findById(id).orElseThrow(()-> new RuntimeException("HotelCommentReview is not found"));
        hotelCommentReviewRepository.delete(review);
    }


    public List<HotelCommentReviewResponse> getHotelCommentReviewByHotelId(Integer hotelId) {
        hotelRepository.findById(hotelId).orElseThrow(()-> new RuntimeException("Hotel is not found"));
        List<HotelCommentReview> reviews = hotelCommentReviewRepository.findByHotelId(hotelId);
        List<HotelCommentReviewResponse> responses = new ArrayList<>();
        for(HotelCommentReview review : reviews){
            HotelCommentReviewResponse response = HotelCommentReviewResponse.builder()
                    .id(review.getId())
                    .comment(review.getComment())
                    .hotel(review.getHotel())
                    .createdAt(review.getCreatedAt())
                    .client(review.getClient())
                    .build();
            responses.add(response);
        }
        return responses;

    }
}
