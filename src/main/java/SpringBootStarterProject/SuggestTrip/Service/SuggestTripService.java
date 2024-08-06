package SpringBootStarterProject.SuggestTrip.Service;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.City_Place_Package.Repository.PlaceRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ManagingPackage.exception.UnAuthorizedException;
import SpringBootStarterProject.SuggestTrip.Model.SuggestedTrip;
import SpringBootStarterProject.SuggestTrip.Repository.SuggestedTripRepository;
import SpringBootStarterProject.SuggestTrip.Request.SuggestTripRequest;
import SpringBootStarterProject.SuggestTrip.Response.SuggestedTripResponse;
import SpringBootStarterProject.Trippackage.Models.TripServices;
import SpringBootStarterProject.Trippackage.Repository.TripRepository;
import SpringBootStarterProject.Trippackage.Repository.TripServicesRepository;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SuggestTripService {

    private final TripRepository tripRepository;
    private final SuggestedTripRepository suggestedTripRepository;
    private final ObjectsValidator<SuggestTripRequest> suggestTripValidator;
    private final TripServicesRepository tripServicesRepository;
    private final ClientRepository clientRepository;
    private final PlaceRepository placeRepository;

    public ApiResponseClass findAll() {
        List<SuggestedTrip> suggestedTrips = suggestedTripRepository.findAll();
        List<SuggestedTripResponse> responses = new ArrayList<>();
        for (SuggestedTrip suggestedTrip : suggestedTrips) {
            responses.add(SuggestedTripResponse.builder()
                    .num_of_passenger(suggestedTrip.getNum_of_passenger())
                    .suggestedTripId(suggestedTrip.getId())
                    .transportation_type(suggestedTrip.getTransportation_type())
                    .user_id(suggestedTrip.getUser().getId())
                    .date_of_departure(suggestedTrip.getDate_of_departure())
                    .trip_service(suggestedTrip.getTrip_service().stream().map(TripServices::getName).toList())
                    .places(suggestedTrip.getPlaces().stream().map(Place::getName).toList())
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
                .user_id(suggestedTrip.getUser().getId())
                .date_of_departure(suggestedTrip.getDate_of_departure())
                .trip_service(suggestedTrip.getTrip_service().stream().map(TripServices::getName).toList())
                .places(suggestedTrip.getPlaces().stream().map(Place::getName).toList())
                .build();
        return new ApiResponseClass("Get suggested trip by id" , HttpStatus.ACCEPTED , LocalDateTime.now(), suggestedTripResponse);
    }

    public ApiResponseClass findByUserId(Integer userId) {
        List<SuggestedTrip> suggestedTrips = suggestedTripRepository.findByUser(clientRepository.findById(userId).orElseThrow(
                ()-> new RequestNotValidException("suggested-trips with user_id: " + userId + " not found")
        ));
        List<SuggestedTripResponse> responses = new ArrayList<>();
        for (SuggestedTrip suggestedTrip : suggestedTrips) {
            responses.add(SuggestedTripResponse.builder()
                    .num_of_passenger(suggestedTrip.getNum_of_passenger())
                    .suggestedTripId(suggestedTrip.getId())
                    .transportation_type(suggestedTrip.getTransportation_type())
                    .user_id(suggestedTrip.getUser().getId())
                    .date_of_departure(suggestedTrip.getDate_of_departure())
                    .trip_service(suggestedTrip.getTrip_service().stream().map(TripServices::getName).toList())
                    .places(suggestedTrip.getPlaces().stream().map(Place::getName).toList())
                    .build()
            );
        }
        return new ApiResponseClass("Get suggested trips by user_id" , HttpStatus.ACCEPTED , LocalDateTime.now(), responses);
    }

    public ApiResponseClass createSuggestTrip(SuggestTripRequest request ) {

        suggestTripValidator.validate(request);

        List<TripServices> tripServices = new ArrayList<>();
        for(String service : request.getTripService()){
            tripServices.add(tripServicesRepository.findByName(service).orElseThrow(
                    ()-> new RequestNotValidException("trip service not found")
            ));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var suggester_client = clientRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found"));


        if(suggester_client == null){
            throw new RequestNotValidException("client not found");
        }
        List<Place> placeList ;
        try {

            placeList =  placeRepository.findAllById(request.getPlaces());
        }
        catch(RequestNotValidException e){
            throw new RequestNotValidException("place not found, please try again: " + e.getMessage());
        }
        SuggestedTrip new_suggest_trip = SuggestedTrip.builder()
                .num_of_passenger(request.getNum_of_passengers())
                .date_of_departure(request.getDate_of_departure())
                .transportation_type(request.getTransportations())
                .user(suggester_client)
                .trip_service(tripServices)
                .places(placeList)
                .build();
        suggestedTripRepository.save(new_suggest_trip);

        SuggestedTripResponse response = SuggestedTripResponse.builder()
                .num_of_passenger(new_suggest_trip.getNum_of_passenger())
                .suggestedTripId(new_suggest_trip.getId())
                .transportation_type(new_suggest_trip.getTransportation_type())
                .user_id(new_suggest_trip.getUser().getId())
                .date_of_departure(new_suggest_trip.getDate_of_departure())
                .trip_service(new_suggest_trip.getTrip_service().stream().map(TripServices::getName).toList())
                .places(new_suggest_trip.getPlaces().stream().map(Place::getName).toList())
                .build();
        return new ApiResponseClass("Create suggested trip Done successfully" , HttpStatus.CREATED , LocalDateTime.now(), response);
    }


    // ToDo: Read below
    public ApiResponseClass updateSuggestTrip(SuggestTripRequest request ,Integer id) {

        SuggestedTrip suggestedTrip = suggestedTripRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("suggested trip not found")
        );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client info_updater = clientRepository.findByEmail(authentication.getName()).orElseThrow(
                ()-> new RequestNotValidException("client not found")
        );

        // TODO: Handling a manager case

        //        Manager info_updater_manager = (Manager) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        if(info_updater == null){
//            if(info_updater_manager == null ){
                throw new RequestNotValidException("user not found");
//            }
        }
        if(!suggestedTrip.getUser().equals(info_updater) ){
            throw new UnAuthorizedException("client is not the suggester for this trip");
        }

        suggestTripValidator.validate(request);

        List<TripServices> tripServices = new ArrayList<>();
        for(String service : request.getTripService()){
            tripServices.add(tripServicesRepository.findByName(service).orElseThrow(
                    ()-> new RequestNotValidException("trip service not found")
            ));
        }
        List<Place> placeList ;
        try {
            placeList =  placeRepository.findAllById(request.getPlaces());
        }
        catch(RequestNotValidException e){
            throw new RequestNotValidException("place not found, please try again: " + e.getMessage());
        }

        suggestedTrip.setTrip_service(tripServices);
        suggestedTrip.setPlaces(placeList);
        suggestedTrip.setDate_of_departure(request.getDate_of_departure());
        suggestedTrip.setTransportation_type(request.getTransportations());
        suggestedTrip.setNum_of_passenger(request.getNum_of_passengers());
        suggestedTripRepository.save(suggestedTrip);

//        List<String> suggested_trip_services

        SuggestedTripResponse response = SuggestedTripResponse.builder()
                .num_of_passenger(suggestedTrip.getNum_of_passenger())
                .suggestedTripId(suggestedTrip.getId())
                .transportation_type(suggestedTrip.getTransportation_type())
                .user_id(suggestedTrip.getUser().getId())
                .date_of_departure(suggestedTrip.getDate_of_departure())
                .trip_service(suggestedTrip.getTrip_service().stream().map(TripServices::getName).toList())
                .places(suggestedTrip.getPlaces().stream().map(Place::getName).toList())
                .build();
        return new ApiResponseClass("Update suggested trip Done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(), response);
    }

    public ApiResponseClass deleteSuggestTrip(Integer id) {
        SuggestedTrip suggestedTrip = suggestedTripRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("suggested trip not found")
        );
        suggestedTripRepository.delete(suggestedTrip);
        return new ApiResponseClass("Delete suggested trip successfully" , HttpStatus.NO_CONTENT , LocalDateTime.now());
    }

}
