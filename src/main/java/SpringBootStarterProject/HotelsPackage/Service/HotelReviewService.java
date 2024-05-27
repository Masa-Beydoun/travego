package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelReviewRequest;
import SpringBootStarterProject.HotelsPackage.Models.HotelReview;
import SpringBootStarterProject.HotelsPackage.Repository.HotelReviewRepository;
import SpringBootStarterProject.HotelsPackage.Response.HotelReviewResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelReviewService {

    private final HotelReviewRepository hotelReviewRepository;
    private final HotelRepository hotelRepository;
    private final ClientRepository clientRepository;
    private final ObjectsValidator<HotelReviewRequest> validator;


    public List<HotelReviewResponse> findHotelReviewsByHotelId(Integer hotelId) {
        hotelRepository.findById(hotelId).orElseThrow(()->new RequestNotValidException("Hotel not found"));
        List<HotelReview> hotelReviews = hotelReviewRepository.findByHotelId(hotelId);
        List<HotelReviewResponse> hotelReviewResponses = new ArrayList<>();
        for (HotelReview review : hotelReviews) {
            HotelReviewResponse hotelReviewResponse = HotelReviewResponse.builder()
                    .id(review.getId())
                    .hotel(review.getHotel())
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
        return hotelReviewResponses;
    }

    public HotelReviewResponse save(HotelReviewRequest request) {
        validator.validate(request);

        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(()->new RequestNotValidException("Hotel not found"));
        Client client = clientRepository.findById(request.getClientId()).orElseThrow(()->new RequestNotValidException("Client not found"));

        double avg = (request.getCleanliness() + request.getSecurity() + request.getLocation() + request.getFacilities()) / 4.0;

        HotelReview newHotelReview = HotelReview.builder()
                .hotel(hotel)
                .averageRating(avg)
                .cleanliness(request.getCleanliness())
                .facilities(request.getFacilities())
                .location(request.getLocation())
                .security(request.getSecurity())
                .reviewDate(LocalDate.now())
                .client(client)
                .build();

        hotelReviewRepository.save(newHotelReview);
        return HotelReviewResponse.builder()
                .id(newHotelReview.getId())
                .cleanliness(newHotelReview.getCleanliness())
                .security(newHotelReview.getSecurity())
                .location(newHotelReview.getLocation())
                .facilities(newHotelReview.getFacilities())
                .averageRating(newHotelReview.getAverageRating())
                .client(newHotelReview.getClient())
                .hotel(newHotelReview.getHotel())
                .reviewDate(newHotelReview.getReviewDate())
                .build();
    }

    public void delete(Integer reviewId) {
        HotelReview hotelReview = hotelReviewRepository.findById(reviewId).orElseThrow(()->new RequestNotValidException("Hotel not found"));
        hotelReviewRepository.delete(hotelReview);
    }


}
