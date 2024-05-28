package SpringBootStarterProject.Trip_package.Service;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trip_package.Models.TripServices;
import SpringBootStarterProject.Trip_package.Repository.TripServicesRepository;
//import SpringBootStarterProject.Trip_package.Request.GetTripServicesByIdRequest;
import SpringBootStarterProject.Trip_package.Request.TripServicesRequest;
import SpringBootStarterProject.Trip_package.Response.TripServicesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServicesService {

    @Autowired
    private final TripServicesRepository tripServicesRepository;

    @Autowired
    private final ObjectsValidator<TripServicesRequest> tripServicesValidator;

//    @Autowired
//    private final ObjectsValidator<GetTripServicesByIdRequest> getTripServicesByIdValidator;

    public ApiResponseClass getAllTripServices() {
        List<TripServices> tripServices =  tripServicesRepository.findAll();
        List<TripServicesResponse> tripServicesResponses = new ArrayList<>();
        for (TripServices tripService : tripServices) {
            tripServicesResponses.add(TripServicesResponse.builder()
                    .id(tripService.getId())
                    .name(tripService.getName())
                    .build());
        }
        return new ApiResponseClass("Get all by trip-services done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(),tripServicesResponses) ;
    }

    public ApiResponseClass getTripServiceById(Integer id) {
//        getTripServicesByIdValidator.validate(id);
        TripServices tripServices = tripServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Service not found")
        );
        TripServicesResponse response = TripServicesResponse.builder()
                .id(tripServices.getId())
                .name(tripServices.getName())
                .build();
        return new ApiResponseClass("Get trip-service by id done successfully" ,HttpStatus.ACCEPTED , LocalDateTime.now() , response );
    }

    public ApiResponseClass createTripService(TripServicesRequest request) {
        tripServicesValidator.validate(request);

        TripServices tripServices = TripServices.builder()
                .name(request.getTripName())
                .build();
        tripServicesRepository.save(tripServices);

        TripServicesResponse response = TripServicesResponse.builder()
                .id(tripServices.getId())
                .name(tripServices.getName())
                .build();
        return new ApiResponseClass("Create trip-service done successfully" ,HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    public ApiResponseClass updateTripService(TripServicesRequest request , Integer id) {
        tripServicesValidator.validate(request);
//        getTripServicesByIdValidator.validate(id);

        TripServices tripServices = tripServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Service not found")
        );
        tripServices.setName(request.getTripName());
        tripServicesRepository.save(tripServices);

        TripServicesResponse response = TripServicesResponse.builder()
                .id(tripServices.getId())
                .name(tripServices.getName())
                .build();
        return new ApiResponseClass("Update trip-service done successfully" ,HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }


    public ApiResponseClass deleteTripService(Integer id) {
        TripServices tripServices = tripServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Service not found")
        );
        tripServicesRepository.delete(tripServices);
        return new ApiResponseClass("Delete service done successfully" ,HttpStatus.ACCEPTED , LocalDateTime.now());
    }

}
