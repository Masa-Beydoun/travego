package SpringBootStarterProject.Trip_package.Service;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.City_Place_Package.Repository.PlaceRepository;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.Trip_package.Models.TripPlan;
import SpringBootStarterProject.Trip_package.Repository.TripPlanRepository;
import SpringBootStarterProject.Trip_package.Repository.TripRepository;
import SpringBootStarterProject.Trip_package.Request.TripPlanRequest;
import SpringBootStarterProject.Trip_package.Response.TripPlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripPlanService {

    @Autowired
    private final TripPlanRepository tripPlanRepository;

    private final ObjectsValidator<TripPlanRequest> tripPlanValidator;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private TripRepository tripRepository;

    public List<TripPlanResponse> getAllTripPlans() {
        List<TripPlan> tripPlans = tripPlanRepository.findAll();
        List<TripPlanResponse> tripPlansResponse = new ArrayList<>();
        for (TripPlan tripPlan : tripPlans) {
            tripPlansResponse.add(TripPlanResponse.builder()
                    .id(tripPlan.getId())
                    .description(tripPlan.getDescription())
                    .start_time(tripPlan.getStartDate())
                    .end_time(tripPlan.getEndDate())
                    .trip_id(tripPlan.getTrip().getId())
                    .place_id(tripPlan.getPlace().getId())
                    .build());
        }
        return tripPlansResponse;
    }

    public TripPlanResponse getTripPlanById(Integer id) {
     TripPlan tripPlan =   tripPlanRepository.findById(id)
             .orElseThrow(
                     ()-> new RequestNotValidException("trip plan does not exist")
             );

    return TripPlanResponse.builder()
            .id(tripPlan.getId())
            .description(tripPlan.getDescription())
            .start_time(tripPlan.getStartDate())
            .end_time(tripPlan.getEndDate())
            .trip_id(tripPlan.getTrip().getId())
            .place_id(tripPlan.getPlace().getId())
            .build();
    }

    public List<TripPlanResponse> getTripPlanByTripId(Integer trip_id) {
        List<TripPlan> tripPlans = tripPlanRepository.findByTripId(trip_id);

        List<TripPlanResponse> tripPlansResponse = new ArrayList<>();

        for (TripPlan tripPlan : tripPlans) {
            tripPlansResponse.add(TripPlanResponse.builder()
                    .id(tripPlan.getId())
                    .description(tripPlan.getDescription())
                    .start_time(tripPlan.getStartDate())
                    .end_time(tripPlan.getEndDate())
                    .trip_id(tripPlan.getTrip().getId())
                    .place_id(tripPlan.getPlace().getId())
                    .build());
        }
        return tripPlansResponse;
    }

    public TripPlanResponse createTripPlan(TripPlanRequest request) {
        tripPlanValidator.validate(request);

        Place place = placeRepository.findById(request.getPlace_id()).orElseThrow(
                ()-> new RequestNotValidException("place does not exist")
        );
        Trip trip = tripRepository.findById(request.getTrip_id()).orElseThrow(
                ()-> new RequestNotValidException("trip does not exist")
        );
        TripPlan tripPlan = TripPlan.builder()
                .description(request.getDescription())
                .place(place)
                .trip(trip)
                .startDate(request.getStart_date())
                .endDate(request.getEnd_date())
                .build();
        tripPlanRepository.save(tripPlan);

        return TripPlanResponse.builder()
                .id(tripPlan.getId())
                .description(tripPlan.getDescription())
                .start_time(tripPlan.getStartDate())
                .end_time(tripPlan.getEndDate())
                .trip_id(tripPlan.getTrip().getId())
                .place_id(tripPlan.getPlace().getId())
                .build();
    }

    public TripPlanResponse updateTripPlan(TripPlanRequest request , Integer id) {
        TripPlan tripPlan = tripPlanRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("trip plan does not exist")
        );
        tripPlanValidator.validate(request);

        Place place = placeRepository.findById(request.getPlace_id()).orElseThrow(
                ()-> new RequestNotValidException("place does not exist")
        );
        Trip trip = tripRepository.findById(request.getTrip_id()).orElseThrow(
                ()-> new RequestNotValidException("trip does not exist")
        );

        tripPlan.setDescription(request.getDescription());
        tripPlan.setPlace(place);
        tripPlan.setTrip(trip);
        tripPlan.setStartDate(request.getStart_date());
        tripPlan.setEndDate(request.getEnd_date());
        tripPlanRepository.save(tripPlan);

        return TripPlanResponse.builder()
                .id(tripPlan.getId())
                .description(tripPlan.getDescription())
                .start_time(tripPlan.getStartDate())
                .end_time(tripPlan.getEndDate())
                .trip_id(tripPlan.getTrip().getId())
                .place_id(tripPlan.getPlace().getId())
                .build();
    }


    public String deleteTripPlan(Integer id) {
        TripPlan tripPlan = tripPlanRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("trip plan does not exist")
        );
        tripPlanRepository.delete(tripPlan);
        return "Trip plan successfully deleted";
    }

}
