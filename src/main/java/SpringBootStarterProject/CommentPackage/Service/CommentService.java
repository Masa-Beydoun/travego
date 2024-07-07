package SpringBootStarterProject.CommentPackage.Service;

import SpringBootStarterProject.CommentPackage.Enum.CommentType;
import SpringBootStarterProject.CommentPackage.Models.Comment;
import SpringBootStarterProject.CommentPackage.Response.TripCommentResponse;
import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservation;
import SpringBootStarterProject.HotelReservationPackage.Repository.HotelReservationRepository;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.CommentPackage.Repository.CommentRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.CommentPackage.Request.CommentRequest;
import SpringBootStarterProject.CommentPackage.Response.HotelCommentResponse;
import SpringBootStarterProject.HotelsPackage.Service.HotelService;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.Trip_package.Repository.TripRepository;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static SpringBootStarterProject.CommentPackage.Enum.CommentType.TRIP;

@Service
@RequiredArgsConstructor()
public class CommentService {
    private final CommentRepository commentRepository;
    private final ObjectsValidator<CommentRequest> validator;
    private final ClientRepository clientRepository;
    private final HotelRepository hotelRepository;
    private final HotelDetailsRepository hotelDetailsRepository;
    private final TripRepository tripRepository;
    private final HotelReservationRepository hotelReservationRepository;

    public ApiResponseClass addTripComment(CommentRequest request){
        validator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Trip trip= tripRepository.findById(request.getHotelDetailsId()).orElseThrow(()-> new RequestNotValidException("trip is not found"));


        Comment comment = Comment.builder()
                .comment(request.getComment())
                .typeId(trip.getId())
                .type(TRIP)
                .createdAt(LocalDateTime.now())
                .client(client)
                .build();

        commentRepository.save(comment);

        TripCommentResponse response = TripCommentResponse.builder()
                .id(comment.getId())
                .createdAt(comment.getCreatedAt())
                .client(comment.getClient())
                .comment(comment.getComment())
                .build();
        return  new ApiResponseClass("Comment added successfully",HttpStatus.CREATED,LocalDateTime.now(),response);
        //TODO : later when there is reservation .. check the ability for adding a comment

    }

    public ApiResponseClass deleteComment(Integer id){
        Comment review = commentRepository.findById(id).orElseThrow(()-> new RequestNotValidException("HotelCommentReview is not found"));
        commentRepository.delete(review);
        return new ApiResponseClass("Deleted successfully",HttpStatus.OK,LocalDateTime.now());
    }


    public ApiResponseClass getHotelCommentReviewByHotelDetailsId(Integer id) {

        List<Comment> reviews = commentRepository.findByTypeId(id);

        if(reviews.get(0).getType() == TRIP){
            List<TripCommentResponse> responses = new ArrayList<>();
            Trip trip = tripRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Trip is not found"));
            for(Comment review : reviews){
                TripCommentResponse response = TripCommentResponse.builder()
                        .id(review.getId())
                        .comment(review.getComment())
                        .trip(trip)
                        .createdAt(review.getCreatedAt())
                        .client(review.getClient())
                        .build();
                responses.add(response);
            }
            return new ApiResponseClass("Get Comments by Hotel Id done", HttpStatus.OK,LocalDateTime.now(),responses);

        }
        else{
            List<HotelCommentResponse> responses = new ArrayList<>();
            HotelDetails hotelDetails = hotelDetailsRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Trip is not found"));
            for(Comment review : reviews){
                HotelCommentResponse response = HotelCommentResponse.builder()
                        .id(review.getId())
                        .comment(review.getComment())
                        .hotelDetails(hotelDetails)
                        .createdAt(review.getCreatedAt())
                        .client(review.getClient())
                        .build();
                responses.add(response);
            }
            return new ApiResponseClass("Get Comments by Hotel Id done", HttpStatus.OK,LocalDateTime.now(),responses);

        }





    }

    public ApiResponseClass addHotelComment(CommentRequest request) {
        validator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        HotelDetails hotelDetails= hotelDetailsRepository.findById(request.getHotelDetailsId()).orElseThrow(()-> new RequestNotValidException("Hotel-Details is not found"));

        List<HotelReservation> reservation = hotelReservationRepository.findByHotelIdAndClientId(hotelDetails.getHotel().getId(),client.getId()).orElse(null);
        if(reservation == null){
            throw new RequestNotValidException("No Reservation has been made is not found");
        }

        Comment comment = Comment.builder()
                .comment(request.getComment())
                .typeId(hotelDetails.getId())
                .type(CommentType.HOTEL)
                .createdAt(LocalDateTime.now())
                .client(client)
                .build();

        commentRepository.save(comment);

        HotelCommentResponse response = HotelCommentResponse.builder()
                .id(comment.getId())
                .hotelDetails(hotelDetails)
                .createdAt(comment.getCreatedAt())
                .client(comment.getClient())
                .comment(comment.getComment())
                .build();
        return  new ApiResponseClass("Comment added successfully",HttpStatus.CREATED,LocalDateTime.now(),response);

    }
}
