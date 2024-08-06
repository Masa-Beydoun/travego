package SpringBootStarterProject.ReviewsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ReviewsPackage.Models.HotelReview;
import SpringBootStarterProject.ReviewsPackage.Models.TripReview;
import SpringBootStarterProject.ReviewsPackage.Repository.TripReviewRepository;
import SpringBootStarterProject.ReviewsPackage.Request.TripReviewRequest;
import SpringBootStarterProject.ReviewsPackage.Response.AllTripReviewResponse;
import SpringBootStarterProject.ReviewsPackage.Response.HotelReviewResponse;
import SpringBootStarterProject.ReviewsPackage.Response.TripReviewResponse;
import SpringBootStarterProject.Trippackage.Models.Trip;
import SpringBootStarterProject.Trippackage.Repository.TripRepository;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripReviewService {
    private final ObjectsValidator<TripReviewRequest> validator;

    private final TripRepository tripRepository;
    private final TripReviewRepository tripReviewRepository;
    private final ClientRepository clientRepository;
    public ApiResponseClass findTripReviewsId(Integer tripId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }


        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));



        Trip trip = tripRepository.findById(tripId).orElseThrow(()-> new RequestNotValidException("Trip Not Found"));


        List<TripReview> tripReviews = tripReviewRepository.findByTripId(tripId).orElse(null);
        if(tripReviews == null){
            TripReviewResponse review = TripReviewResponse.builder()
                    .tripId(tripId)
                    .reviewDate(null)
                    .services(5.0)
                    .generalRating(5.0)
                    .places(5.0)
                    .build();
            return new ApiResponseClass("No Reviews yet",HttpStatus.OK,LocalDateTime.now(),review);
        }



        Integer totalReviews = tripReviews.size();
        Double plac=0.0,gen=0.0,ser=0.0;

        for (TripReview review : tripReviews) {
            plac+=review.getPlaces();
            gen+=review.getGeneralRating();
            ser+=review.getServices();
        }
        AllTripReviewResponse response= AllTripReviewResponse.builder()
                .tripId(tripId)
                .places(plac/totalReviews)
                .services(ser/totalReviews)
                .generalRating(gen/totalReviews)
                .build();
        return new ApiResponseClass("Reviews Got successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }


    public ApiResponseClass save(TripReviewRequest request) {
        validator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));


        Trip trip = tripRepository.findById(request.getTripId()).orElseThrow(()-> new RequestNotValidException("Trip Not Found"));


        TripReview sameClient= tripReviewRepository.findByClientIdAndTripId(client.getId(), trip.getId()).orElse(null);

        if(sameClient != null) {
            throw new RequestNotValidException("You have already reviewed this trip");
        }


        TripReview review = TripReview.builder()
                .trip(trip)
                .places(request.getPlaces())
                .generalRating(request.getGeneralRating())
                .services(request.getServices())
                .client(client)
                .reviewDate(LocalDate.now())
                .build();
        tripReviewRepository.save(review);

        TripReviewResponse response = TripReviewResponse.builder()
                .id(review.getId())
                .tripId(review.getTrip().getId())
                .places(review.getPlaces())
                .generalRating(review.getGeneralRating())
                .services(review.getServices())
                .reviewDate(review.getReviewDate())
                .build();
        return new ApiResponseClass("Reviews Saved Successfully", HttpStatus.OK, LocalDateTime.now(), response);


    }
}
