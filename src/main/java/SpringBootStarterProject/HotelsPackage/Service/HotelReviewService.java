package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelReviewRequest;
import SpringBootStarterProject.HotelsPackage.Models.HotelReview;
import SpringBootStarterProject.HotelsPackage.Repository.HotelReviewRepository;
import SpringBootStarterProject.HotelsPackage.Response.HotelReviewResponse;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelReviewService {

    private final HotelReviewRepository hotelReviewRepository;
    private final HotelDetailsRepository hotelDetailsRepository;
    private final HotelRepository hotelRepository;
    private final ClientRepository clientRepository;
    private final ObjectsValidator<HotelReviewRequest> validator;


    public ApiResponseClass findHotelReviewsByHotelDetailsId(Integer hotelDetailsId) {
        HotelDetails details = hotelDetailsRepository.findById(hotelDetailsId).orElseThrow(()->new RequestNotValidException("Hotel-Details not found"));

        List<HotelReview> hotelReviews = hotelReviewRepository.findByHotelDetailsId(hotelDetailsId);
        List<HotelReviewResponse> hotelReviewResponses = new ArrayList<>();
        for (HotelReview review : hotelReviews) {
            HotelReviewResponse hotelReviewResponse = HotelReviewResponse.builder()
                    .id(review.getId())
                    .hotelDetailsId(review.getHotelDetails().getId())
                    .reviewDate(review.getReviewDate())
                    .security(review.getSecurity())
                    .location(review.getLocation())
                    .facilities(review.getFacilities())
                    .cleanliness(review.getCleanliness())
                    .averageRating(review.getAverageRating())
                    .client(review.getClient())
                    .build();
            hotelReviewResponses.add(hotelReviewResponse);
        }
        return new ApiResponseClass("Get Hotel-Review by hotel-details id done", HttpStatus.OK,LocalDateTime.now(),hotelReviewResponses);
    }

    public ApiResponseClass save(HotelReviewRequest request) {
        validator.validate(request);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));


        HotelDetails details = hotelDetailsRepository.findById(request.getHotelDetailsId()).orElseThrow(()->new RequestNotValidException("Hotel-Details not found"));

        Hotel hotel = hotelRepository.findById(details.getHotel().getId()).orElseThrow(()->new RequestNotValidException("Hotel not found"));

        HotelReview sameClient= hotelReviewRepository.findByClientIdAndHotelDetailsId(client.getId(), details.getId());
        if(sameClient != null) {
            throw new RequestNotValidException("Hotel-Review already exists");
        }

        //calculating the new rate in the origin hotelDetails


        double avg = (request.getCleanliness() + request.getSecurity() + request.getLocation() + request.getFacilities()) / 4.0;

        HotelReview newHotelReview = HotelReview.builder()
                .hotelDetails(details)
                .averageRating(avg)
                .cleanliness(request.getCleanliness())
                .facilities(request.getFacilities())
                .location(request.getLocation())
                .security(request.getSecurity())
                .reviewDate(LocalDate.now())
                .client(client)
                .build();

        hotelReviewRepository.save(newHotelReview);
        details.setNumOfReviews(details.getNumOfReviews() + 1);
        Integer num = details.getNumOfReviews();

        details.setCleanliness((details.getCleanliness() + request.getCleanliness())/num);
        details.setFacilities((details.getFacilities() + request.getFacilities()/num));
        details.setSecurity((details.getSecurity() + request.getSecurity())/num);
        details.setLocation((details.getLocation() + request.getLocation())/num);

        details.getReviews().add(newHotelReview);

        Double cle = details.getCleanliness();
        Double fac = details.getFacilities();
        Double sec = details.getSecurity();
        Double loc = details.getLocation();

        details.setAverageRating((cle+fac+sec+loc)/4/num);
        hotelDetailsRepository.save(details);

        HotelReviewResponse response =  HotelReviewResponse.builder()
                .id(newHotelReview.getId())
                .cleanliness(newHotelReview.getCleanliness())
                .security(newHotelReview.getSecurity())
                .location(newHotelReview.getLocation())
                .facilities(newHotelReview.getFacilities())
                .averageRating(newHotelReview.getAverageRating())
                .client(newHotelReview.getClient())
                .hotelDetailsId(newHotelReview.getHotelDetails().getId())
                .reviewDate(newHotelReview.getReviewDate())
                .build();
        return new ApiResponseClass("Hotel-Review created successfully",HttpStatus.CREATED,LocalDateTime.now(),response);
    }

    public ApiResponseClass delete(Integer reviewId) {
        HotelReview hotelReview = hotelReviewRepository.findById(reviewId).orElseThrow(()->new RequestNotValidException("Hotel-Review not found"));
        hotelReviewRepository.delete(hotelReview);
        return new ApiResponseClass("Hotel-Review deleted successfully",HttpStatus.OK,LocalDateTime.now());
    }


}
