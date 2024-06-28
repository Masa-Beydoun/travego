package SpringBootStarterProject.SuggestTrip.Service;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.SuggestTrip.Model.SuggestedTrip;
import SpringBootStarterProject.SuggestTrip.Repository.SuggestedTripRepository;
import SpringBootStarterProject.SuggestTrip.Request.SuggestTripRequest;
import SpringBootStarterProject.SuggestTrip.Response.SuggestedTripResponse;
import SpringBootStarterProject.Trip_package.Repository.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SuggestTripService {

    private final TripRepository tripRepository;
    private final SuggestedTripRepository suggestedTripRepository;

    public ApiResponseClass findAll() {
        List<SuggestedTrip> suggestedTrips = suggestedTripRepository.findAll();
        List<SuggestedTripResponse> responses = new ArrayList<>();
        for (SuggestedTrip suggestedTrip : suggestedTrips) {
            responses.add(SuggestedTripResponse.builder()
                    .num_of_passenger(suggestedTrip.getNum_of_passenger())
                    .suggestedTripId(suggestedTrip.getId())
                    .transportation_type(suggestedTrip.getTransportation_type())
                    .user_id(suggestedTrip.getUser_id())
                    .date_of_departure(suggestedTrip.getDate_of_departure())
                    .trip_service(suggestedTrip.getTrip_service())
                    .places(suggestedTrip.getPlaces())
                    .build()
            );
        }
        return new ApiResponseClass("Get all suggested-trip done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(), responses);
    }

    public ApiResponseClass findById(Integer id) {
        SuggestedTrip suggestedTrip = suggestedTripRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("suggested-trip with id: " + id + " not found")
        );
        SuggestedTripResponse suggestedTripResponse = SuggestedTripResponse.builder()
                .num_of_passenger(suggestedTrip.getNum_of_passenger())
                .suggestedTripId(suggestedTrip.getId())
                .transportation_type(suggestedTrip.getTransportation_type())
                .user_id(suggestedTrip.getUser_id())
                .date_of_departure(suggestedTrip.getDate_of_departure())
                .trip_service(suggestedTrip.getTrip_service())
                .places(suggestedTrip.getPlaces())
                .build();
        return new ApiResponseClass("Get suggested trip by id" , HttpStatus.ACCEPTED , LocalDateTime.now(), suggestedTripResponse);
    }
//    public ApiResponseClass findByUserId(String userId) {
//        List<SuggestedTrip> suggestedTrips = suggestedTripRepository.findByUserId(userId);
//        List<SuggestedTripResponse> responses = new ArrayList<>();
//        for (SuggestedTrip suggestedTrip : suggestedTrips) {
//            responses.add(SuggestedTripResponse.builder()
//                    .num_of_passenger(suggestedTrip.getNum_of_passenger())
//                    .suggestedTripId(suggestedTrip.getId())
//                    .transportation_type(suggestedTrip.getTransportation_type())
//                    .user_id(suggestedTrip.getUser_id())
//                    .date_of_departure(suggestedTrip.getDate_of_departure())
//                    .trip_service(suggestedTrip.getTrip_service())
//                    .places(suggestedTrip.getPlaces())
//                    .build()
//            );
//        }
//        return new ApiResponseClass("Get suggested trips by user_id" , HttpStatus.ACCEPTED , LocalDateTime.now(), responses);
//    }

    public ApiResponseClass createSuggestTrip(SuggestTripRequest request) {

    }

}
