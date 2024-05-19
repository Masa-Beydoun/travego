package SpringBootStarterProject.Trip_package.Service;

import SpringBootStarterProject.City_package.Models.City;
import SpringBootStarterProject.City_package.Repository.CityRepository;
import SpringBootStarterProject.City_package.Repository.CountryRepository;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.Trip_package.Models.TripServices;
import SpringBootStarterProject.Trip_package.Repository.TripPlanRepository;
import SpringBootStarterProject.Trip_package.Repository.TripPriceRepository;
import SpringBootStarterProject.Trip_package.Repository.TripRepository;
import SpringBootStarterProject.Trip_package.Repository.TripServiceRepository;
import SpringBootStarterProject.Trip_package.Request.TripRequest;
import SpringBootStarterProject.Trip_package.Response.TripResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {


    private TripRepository tripRepository;
    private TripPriceRepository tripPriceRepository;
    private TripPlanRepository tripPlanRepository;
    private TripServiceRepository tripServiceRepository;

    @Autowired
    private ObjectsValidator<TripRequest> tripRequestValidator;
    private CountryRepository countryRepository;
    @Autowired
    private CityRepository cityRepository;

    public List<TripResponse> getTrips() {
        List<Trip>  tripList =  tripRepository.findAll();
        List<TripResponse> tripResponseList = new ArrayList<>();
        for(Trip trip : tripList){
            Integer totalPrice =
                    trip.getPrice().getFlightPrice()
                            + trip.getPrice().getHotelPrice()
                            + trip.getPrice().getServicesPrice();
            String isPrivate = "public";
            if(trip.getIsPrivate()){
                isPrivate = "private";
            }

                    tripResponseList.add(TripResponse.builder()
                    .tripId(trip.getId())
                    .tripName(trip.getName())
                    .tripDescription(trip.getDescription())
                    .tripCategory(trip.getTripCategory())
                    .tripStartDate(trip.getStartDate())
                    .tripEndDate(trip.getEndDate())
                    .country(trip.getCountry().getName())
                    .cities(trip.getCities().stream().map(City::getName).toList())
                    .flightCompany(trip.getFlightCompany())
                    .min_passengers(trip.getMin_passengers())
                    .max_passengers(trip.getMax_passengers())
                    .status(trip.getStatus())
                    .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                    .price(totalPrice)
                            .isPrivate(isPrivate)
                    .build());
        }
        return tripResponseList;
    }

    public TripResponse getTripById(Integer id) {
        Trip trip = tripRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Id not found"));
        Integer totalPrice =
                trip.getPrice().getFlightPrice()
                        + trip.getPrice().getHotelPrice()
                        + trip.getPrice().getServicesPrice();
        String isPrivate = "public";
        if(trip.getIsPrivate()){
            isPrivate = "private";
        }
        return TripResponse.builder()
                .tripId(trip.getId())
                .tripName(trip.getName())
                .tripDescription(trip.getDescription())
                .tripCategory(trip.getTripCategory())
                .tripStartDate(trip.getStartDate())
                .tripEndDate(trip.getEndDate())
                .country(trip.getCountry().getName())
                .cities(trip.getCities().stream().map(City::getName).toList())
                .flightCompany(trip.getFlightCompany())
                .min_passengers(trip.getMin_passengers())
                .max_passengers(trip.getMax_passengers())
                .status(trip.getStatus())
                .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                .price(totalPrice)
                .isPrivate(isPrivate)
                .build();
    }

//    @Transactional
//    public TripResponse createTrip(TripRequest request) {
//        tripRequestValidator.validate(request);
//        Trip trip = Trip.builder()
//                .name(request.getTripName())
//                .description(request.getTripDescription())
//                .tripCategory(request.getTripCategory())
//                .flightCompany(request.getFlightCompany())
//                .startDate(request.getTripStartDate())
//                .endDate(request.getTripEndDate())
//                .country(countryRepository.findByName(request.getCountry()).orElseThrow(
//                        ()-> new RequestNotValidException("country not found")))
////                .cities(request.getCities().stream().map(city->cityRepository.findByName(city.getName)))
//
//
//    }


}
