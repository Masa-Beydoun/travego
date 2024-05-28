package SpringBootStarterProject.Trip_package.Service;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Repository.CityRepository;
import SpringBootStarterProject.City_Place_Package.Repository.CountryRepository;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trip_package.Enum.FlightCompany;
import SpringBootStarterProject.Trip_package.Enum.TripCategory;
import SpringBootStarterProject.Trip_package.Enum.TripStatus;
import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.Trip_package.Models.TripPrice;
import SpringBootStarterProject.Trip_package.Models.TripServices;
import SpringBootStarterProject.Trip_package.Repository.TripPlanRepository;
import SpringBootStarterProject.Trip_package.Repository.TripPriceRepository;
import SpringBootStarterProject.Trip_package.Repository.TripRepository;
import SpringBootStarterProject.Trip_package.Repository.TripServicesRepository;
import SpringBootStarterProject.Trip_package.Request.TripRequest;
import SpringBootStarterProject.Trip_package.Response.TripResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {


    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private TripPriceRepository tripPriceRepository;
    @Autowired
    private TripPlanRepository tripPlanRepository;
    @Autowired
    private TripServicesRepository tripServiceRepository;

    @Autowired
    private ObjectsValidator<TripRequest> tripRequestValidator;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private HotelRepository hotelRepository;

    public ApiResponseClass getTrips() {
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
                    .hotels(trip.getHotel().stream().map(Hotel::getName).toList())
                    .flightCompany(trip.getFlightCompany())
                    .min_passengers(trip.getMin_passengers())
                    .max_passengers(trip.getMax_passengers())
                    .status(trip.getStatus())
                    .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                    .price(totalPrice)
                            .isPrivate(isPrivate)
                    .build());
        }
        return new ApiResponseClass("Get All trips done successfully", HttpStatus.ACCEPTED, LocalDateTime.now(),tripResponseList) ;
    }

    public ApiResponseClass getTripById(Integer id) {
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
        TripResponse response = TripResponse.builder()
                .tripId(trip.getId())
                .tripName(trip.getName())
                .tripDescription(trip.getDescription())
                .tripCategory(trip.getTripCategory())
                .tripStartDate(trip.getStartDate())
                .tripEndDate(trip.getEndDate())
                .country(trip.getCountry().getName())
                .cities(trip.getCities().stream().map(City::getName).toList())
                .hotels(trip.getHotel().stream().map(Hotel::getName).toList())
                .flightCompany(trip.getFlightCompany())
                .min_passengers(trip.getMin_passengers())
                .max_passengers(trip.getMax_passengers())
                .status(trip.getStatus())
                .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                .price(totalPrice)
                .isPrivate(isPrivate)
                .build();
        return new ApiResponseClass("Get trip by id done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    @Transactional
    public ApiResponseClass createTrip(TripRequest request) {
        tripRequestValidator.validate(request);

        List<City> cities = new ArrayList<>();
        for (String city : request.getCities()) {
            cities.add(cityRepository.findByName(city).orElseThrow(
                    () -> new RequestNotValidException("City not found")
            ));
        }

        List<Hotel> hotels = new ArrayList<>();
        for(String hotel : request.getHotels()){
            hotels.add(hotelRepository.findByName(hotel).orElseThrow(
                    ()-> new RequestNotValidException("Hotel not found")
            ));
        }

        List<TripServices> services = new ArrayList<>();
        for(String service: request.getTripServices()){
            services.add(tripServiceRepository.findByName(service).orElseThrow(
                    ()->new RequestNotValidException("Service not found")
            ));
        }

        TripPrice tripPrice = TripPrice.builder()
                .servicesPrice(request.getServicesPrice())
                .flightPrice(request.getFlightPrice())
                .hotelPrice(request.getHotelPrice())
                .build();
        tripPriceRepository.save(tripPrice);

        Integer totalPrice =
                request.getFlightPrice() + request.getHotelPrice() + request.getServicesPrice();

        Trip trip = Trip.builder()
                .name(request.getTripName())
                .description(request.getTripDescription())
                .tripCategory(TripCategory.valueOf(request.getTripCategory()))
                .flightCompany(FlightCompany.valueOf(request.getFlightCompany()))
                .startDate(request.getTripStartDate())
                .endDate(request.getTripEndDate())
                .country(countryRepository.findByName(request.getCountry()).orElseThrow(
                        ()-> new RequestNotValidException("country not found")))
                .cities(cities)
                .hotel(hotels)
                .flightCompany(FlightCompany.valueOf(request.getFlightCompany()))
                .min_passengers(request.getMin_passengers())
                .max_passengers(request.getMax_passengers())
                .status(TripStatus.valueOf(request.getStatus()))
                .tripServices(services)
                .price(tripPrice)
                .isPrivate(request.getIsPrivate())
                .build();
        tripRepository.save(trip);


        String isPrivate = "public";
        if(trip.getIsPrivate()){
            isPrivate = "private";
        }
        TripResponse response = TripResponse.builder()
                .tripId(trip.getId())
                .tripName(trip.getName())
                .tripDescription(trip.getDescription())
                .tripCategory(trip.getTripCategory())
                .tripStartDate(trip.getStartDate())
                .tripEndDate(trip.getEndDate())
                .country(trip.getCountry().getName())
                .cities(trip.getCities().stream().map(City::getName).toList())
                .hotels(trip.getHotel().stream().map(Hotel::getName).toList())
                .flightCompany(trip.getFlightCompany())
                .min_passengers(trip.getMin_passengers())
                .max_passengers(trip.getMax_passengers())
                .status(trip.getStatus())
                .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                .price(totalPrice)
                .isPrivate(isPrivate)
                .build();

        return new ApiResponseClass("Create trip successfully", HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    public ApiResponseClass updateTrip(Integer id, TripRequest request) {
        tripRequestValidator.validate(request);

        Trip trip = tripRepository.findById(id).orElseThrow(
                () -> new RequestNotValidException("Id not found"));

        List<City> cities = new ArrayList<>();
        for (String city : request.getCities()) {
            cities.add(cityRepository.findByName(city).orElseThrow(
                    () -> new RequestNotValidException("City not found")
            ));
        }
        List<Hotel> hotels = new ArrayList<>();
        for(String hotel : request.getHotels()){
            hotels.add(hotelRepository.findByName(hotel).orElseThrow(
                    ()-> new RequestNotValidException("Hotel not found")
            ));
        }

        List<TripServices> services = new ArrayList<>();
        for(String service: request.getTripServices()){
            services.add(tripServiceRepository.findByName(service).orElseThrow(
                    ()->new RequestNotValidException("Service not found")
            ));
        }

        TripPrice tripPrice = tripPriceRepository.findById(trip.getPrice().getId()).orElseThrow(
                ()-> new RequestNotValidException("Price not found"));
        tripPrice.setFlightPrice(request.getFlightPrice());
        tripPrice.setHotelPrice(request.getHotelPrice());
        tripPrice.setServicesPrice(request.getServicesPrice());
        tripPriceRepository.save(tripPrice);

        Integer totalPrice =
                request.getFlightPrice() + request.getHotelPrice() + request.getServicesPrice();

        trip.setName(request.getTripName());
        trip.setDescription(request.getTripDescription());
        trip.setTripCategory(TripCategory.valueOf(request.getTripCategory()));
        trip.setFlightCompany(FlightCompany.valueOf(request.getFlightCompany()));
        trip.setMin_passengers(request.getMin_passengers());
        trip.setMax_passengers(request.getMax_passengers());
        trip.setStatus(TripStatus.valueOf(request.getStatus()));
        trip.setStartDate(request.getTripStartDate());
        trip.setEndDate(request.getTripEndDate());
        trip.setCountry(countryRepository.findByName(request.getCountry()).orElseThrow(
                ()-> new RequestNotValidException("Country not found")
        ));
        trip.setCities(cities);
        trip.setHotel(hotels);
        trip.setTripServices(services);
        trip.setPrice(tripPrice);
        trip.setIsPrivate(request.getIsPrivate());

        String isPrivate = "public";
        if(trip.getIsPrivate()){
            isPrivate = "private";
        }

        TripResponse response = TripResponse.builder()
                .tripId(trip.getId())
                .tripName(trip.getName())
                .tripDescription(trip.getDescription())
                .tripCategory(trip.getTripCategory())
                .tripStartDate(trip.getStartDate())
                .tripEndDate(trip.getEndDate())
                .country(trip.getCountry().getName())
                .cities(trip.getCities().stream().map(City::getName).toList())
                .hotels(trip.getHotel().stream().map(Hotel::getName).toList())
                .flightCompany(trip.getFlightCompany())
                .min_passengers(trip.getMin_passengers())
                .max_passengers(trip.getMax_passengers())
                .status(trip.getStatus())
                .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                .price(totalPrice)
                .isPrivate(isPrivate)
                .build();

        return new ApiResponseClass("Update trip successfully", HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    public ApiResponseClass deleteTrip(Integer id) {
        Trip trip = tripRepository.findById(id).orElseThrow(
                ()->new RequestNotValidException("Id not found")
        );
        tripRepository.delete(trip);
         return new ApiResponseClass("Delete trip successfully", HttpStatus.ACCEPTED , LocalDateTime.now());

    }



}
