package SpringBootStarterProject.Trippackage.Service;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.City_Place_Package.Repository.PlaceRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trippackage.Models.Trip;
import SpringBootStarterProject.Trippackage.Models.TripPlan;
import SpringBootStarterProject.Trippackage.Repository.TripPlanRepository;
import SpringBootStarterProject.Trippackage.Repository.TripRepository;
import SpringBootStarterProject.Trippackage.Request.TripPlanRequest;
import SpringBootStarterProject.Trippackage.Response.TripPlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
// TODO: Add Transactional annotation to required method
public class TripPlanService {

    @Autowired
    private final TripPlanRepository tripPlanRepository;

    private final ObjectsValidator<TripPlanRequest> tripPlanValidator;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private TripRepository tripRepository;

    public ApiResponseClass getAllTripPlans() {
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
        return new ApiResponseClass("Get all trip-plans done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , tripPlansResponse);
    }

    public ApiResponseClass getTripPlanById(Integer id) {
     TripPlan tripPlan =   tripPlanRepository.findById(id)
             .orElseThrow(
                     ()-> new RequestNotValidException("trip plan does not exist")
             );

    TripPlanResponse response = TripPlanResponse.builder()
            .id(tripPlan.getId())
            .description(tripPlan.getDescription())
            .start_time(tripPlan.getStartDate())
            .end_time(tripPlan.getEndDate())
            .trip_id(tripPlan.getTrip().getId())
            .place_id(tripPlan.getPlace().getId())
            .build();

    return new ApiResponseClass("Get trip-plan done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    public ApiResponseClass getTripPlanByTripId(Integer trip_id) {
        List<TripPlan> tripPlans = tripPlanRepository.findByTripIdOrderByStartDate(trip_id);

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
        return new ApiResponseClass("Get trip-plan by trip-id done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , tripPlansResponse);
    }

    public ApiResponseClass createTripPlan(TripPlanRequest request) {
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

        TripPlanResponse response = TripPlanResponse.builder()
                .id(tripPlan.getId())
                .description(tripPlan.getDescription())
                .start_time(tripPlan.getStartDate())
                .end_time(tripPlan.getEndDate())
                .trip_id(tripPlan.getTrip().getId())
                .place_id(tripPlan.getPlace().getId())
                .build();
        return new ApiResponseClass("Create trip-plan done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    public ApiResponseClass updateTripPlan(TripPlanRequest request , Integer id) {
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

        TripPlanResponse response =  TripPlanResponse.builder()
                .id(tripPlan.getId())
                .description(tripPlan.getDescription())
                .start_time(tripPlan.getStartDate())
                .end_time(tripPlan.getEndDate())
                .trip_id(tripPlan.getTrip().getId())
                .place_id(tripPlan.getPlace().getId())
                .build();
        return new ApiResponseClass("Update trip-plan done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() ,response);
    }


    public ApiResponseClass deleteTripPlan(Integer id) {
        TripPlan tripPlan = tripPlanRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("trip plan does not exist")
        );
        tripPlanRepository.delete(tripPlan);
        return new ApiResponseClass("Delete trip-plan done succesffully" , HttpStatus.ACCEPTED , LocalDateTime.now());
    }

}
