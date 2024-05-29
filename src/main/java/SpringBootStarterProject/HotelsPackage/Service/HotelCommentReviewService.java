package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Repository.HotelCommentReviewRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelCommentReviewRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelCommentReviewResponse;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor()
public class HotelCommentReviewService {
    private final HotelCommentReviewRepository hotelCommentReviewRepository;
    private final ObjectsValidator<HotelCommentReviewRequest> validator;
    private final ClientRepository clientRepository;
    private final HotelRepository hotelRepository;
    private final HotelDetailsRepository hotelDetailsRepository;
    private final HotelService hotelService;

    public ApiResponseClass addComment(HotelCommentReviewRequest request){

        validator.validate(request);

        Client client =clientRepository.findById(request.getClientId()).orElseThrow(()-> new RequestNotValidException("Client is not found"));
        HotelDetails hotelDetails= hotelDetailsRepository.findById(request.getHotelDetailsId()).orElseThrow(()-> new RequestNotValidException("Hotel-Details is not found"));

        HotelCommentReview hotelCommentReview = HotelCommentReview.builder()
                .comment(request.getComment())
                .hotelDetails(hotelDetails)
                .createdAt(LocalDateTime.now())
                .client(client)
                .build();

        hotelCommentReviewRepository.save(hotelCommentReview);
//        hotelDetails.getCommentReviews().add(hotelCommentReview);
//        hotelDetailsRepository.save(hotelDetails);

        HotelCommentReviewResponse response = HotelCommentReviewResponse.builder()
                .id(hotelCommentReview.getId())
                .hotelDetails(hotelCommentReview.getHotelDetails())
                .createdAt(hotelCommentReview.getCreatedAt())
                .client(hotelCommentReview.getClient())
                .comment(hotelCommentReview.getComment())
                .build();
        return  new ApiResponseClass("Comment added successfully",HttpStatus.CREATED,LocalDateTime.now(),response);
        //TODO : later when there is reservation .. check the ability for adding a comment
    }

    public ApiResponseClass deleteComment(Integer id){
        HotelCommentReview review = hotelCommentReviewRepository.findById(id).orElseThrow(()-> new RequestNotValidException("HotelCommentReview is not found"));
        hotelCommentReviewRepository.delete(review);
        return new ApiResponseClass("Deleted successfully",HttpStatus.OK,LocalDateTime.now());
    }


    public ApiResponseClass getHotelCommentReviewByHotelDetailsId(Integer hotelDetailsId) {
        hotelDetailsRepository.findById(hotelDetailsId).orElseThrow(()-> new RequestNotValidException("Hotel-Details is not found"));

        List<HotelCommentReview> reviews = hotelCommentReviewRepository.findByHotelDetailsId(hotelDetailsId);
        List<HotelCommentReviewResponse> responses = new ArrayList<>();
        for(HotelCommentReview review : reviews){
            HotelCommentReviewResponse response = HotelCommentReviewResponse.builder()
                    .id(review.getId())
                    .comment(review.getComment())
                    .hotelDetails(review.getHotelDetails())
                    .createdAt(review.getCreatedAt())
                    .client(review.getClient())
                    .build();
            responses.add(response);
        }
        return new ApiResponseClass("Get Comments by Hotel Id done", HttpStatus.OK,LocalDateTime.now(),responses);

    }
}
