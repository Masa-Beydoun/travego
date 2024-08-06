package SpringBootStarterProject.Trippackage.Service;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Repository.CityRepository;
import SpringBootStarterProject.City_Place_Package.Repository.CountryRepository;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.ObjectNotValidException;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trippackage.Enum.FlightCompany;
import SpringBootStarterProject.Trippackage.Enum.TripCategory;
import SpringBootStarterProject.Trippackage.Enum.TripStatus;
import SpringBootStarterProject.Trippackage.Models.Trip;
import SpringBootStarterProject.Trippackage.Models.TripPrice;
import SpringBootStarterProject.Trippackage.Models.TripServices;
import SpringBootStarterProject.Trippackage.Repository.TripPlanRepository;
import SpringBootStarterProject.Trippackage.Repository.TripPriceRepository;
import SpringBootStarterProject.Trippackage.Repository.TripRepository;
import SpringBootStarterProject.Trippackage.Repository.TripServicesRepository;
import SpringBootStarterProject.Trippackage.Request.FilterTripByCategoryRequest;
import SpringBootStarterProject.Trippackage.Request.TripRequest;
import SpringBootStarterProject.Trippackage.Response.TripResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private ObjectsValidator<FilterTripByCategoryRequest> filterTripByCategoryRequestValidator;


    public String isPrivateChecker(Boolean tripIsPrivate){
        String isPrivate = "public";
        if(tripIsPrivate){
            isPrivate = "private";
        }
        return isPrivate;
    }

    public Integer totalPriceCalculator(Integer servicePrice
            , Integer flightPrice
            , Optional<Integer>hotelPrice
    ){
        return servicePrice + flightPrice + hotelPrice.orElse(0);
    }

    public TripPrice tripPriceInit(Integer servicePrice ,
                                   Integer flightPrice , Optional<Integer>hotelPrice ){
        TripPrice tripPrice = TripPrice.builder()
                .servicesPrice(servicePrice)
                .flightPrice(flightPrice)
                .hotelPrice(hotelPrice.orElse(0))
                .build();
        tripPriceRepository.save(tripPrice);
        return tripPrice;
    }

    public Boolean haveHotelsChecker(TripRequest tripRequest){
        if(tripRequest.getHotels() != null){
            return true;
        }
        return false;
    }
    public ApiResponseClass getTripsForClient() {
        List<Trip>  tripList =  tripRepository.findByIsPrivateFalse();
        List<TripResponse> tripResponseList = new ArrayList<>();
        for(Trip trip : tripList){

            Integer totalPrice = totalPriceCalculator(trip.getPrice().getServicesPrice() ,
                    trip.getPrice().getFlightPrice(),
                    Optional.empty());
            List<String> hotelList = new ArrayList<>();
            if(!trip.getHotel().isEmpty()) {
                totalPrice+= trip.getPrice().getHotelPrice();
                hotelList = trip.getHotel().stream().map(Hotel::getName).toList();
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
                        .hotels(Optional.of(hotelList))
                        .flightCompany(trip.getFlightCompany())
                        .min_passengers(trip.getMin_passengers())
                        .max_passengers(trip.getMax_passengers())
                        .status(trip.getStatus())
                        .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                        .price(totalPrice)
                        .isPrivate(isPrivateChecker(trip.getIsPrivate()))
                        .build());
        }

        return new ApiResponseClass("Get All trips done successfully", HttpStatus.ACCEPTED, LocalDateTime.now(),tripResponseList) ;
    }

    public ApiResponseClass getTripById(Integer id) {
        Trip trip = tripRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Id not found"));
//        Integer totalPrice =
//                trip.getPrice().getFlightPrice()
//                        + trip.getPrice().getHotelPrice()
//                        + trip.getPrice().getServicesPrice();
        Integer totalPrice = totalPriceCalculator(trip.getPrice().getServicesPrice() ,
                trip.getPrice().getFlightPrice() ,
                Optional.of(trip.getPrice().getHotelPrice()));

        List<String> hotelList = new ArrayList<>();
        if(!trip.getHotel().isEmpty()) {
//            totalPrice += trip.getPrice().getHotelPrice();
            hotelList = trip.getHotel().stream().map(Hotel::getName).toList();
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
                .hotels(Optional.of(hotelList))
                .flightCompany(trip.getFlightCompany())
                .min_passengers(trip.getMin_passengers())
                .max_passengers(trip.getMax_passengers())
                .status(trip.getStatus())
                .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                .price(totalPrice)
                .isPrivate(isPrivateChecker(trip.getIsPrivate()))
                .build();
        return new ApiResponseClass("Get trip by id done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    @Transactional
    public ApiResponseClass createTrip(TripRequest request) {
        tripRequestValidator.validate(request);

        List<City> cities = new ArrayList<>();
        for (Integer city : request.getCities()) {
            cities.add(cityRepository.findById(city).orElseThrow(
                    () -> new RequestNotValidException("City not found")
            ));
        }
//        List<Hotel> hotels = new ArrayList<>();
//        if (request.getHotels() != null) {
//            for(Integer hotel : request.getHotels()){
//                hotels.add(hotelRepository.findById(hotel).orElseThrow(
//                        ()-> new RequestNotValidException("Hotel not found")
//                ));
//            }
//        }

        List<TripServices> services = new ArrayList<>();
        for(String service: request.getTripServices()){
            services.add(tripServiceRepository.findByName(service).orElseThrow(
                    ()->new RequestNotValidException("Service not found")
            ));
        }
//        if(request.getTripCategory().equals)

        TripPrice tripPrice = tripPriceInit(
                request.getServicesPrice(),
                request.getFlightPrice(),
                request.getHotelPrice()
        );
        tripPriceRepository.save(tripPrice);

        Integer totalPrice = totalPriceCalculator(request.getServicesPrice()
        , request.getFlightPrice(), request.getHotelPrice());

//                request.getFlightPrice() + request.getServicesPrice();

        Trip trip = Trip.builder()
                .name(request.getTripName())
                .description(request.getTripDescription())
                .tripCategory(TripCategory.valueOf(request.getTripCategory()))
                .flightCompany(FlightCompany.valueOf(request.getFlightCompany()))
                .startDate(request.getTripStartDate())
                .endDate(request.getTripEndDate())
                .country(countryRepository.findById(request.getCountry()).orElseThrow(
                        ()-> new RequestNotValidException("country not found")))
                .cities(cities)
//                .hotel(hotels)
                .flightCompany(FlightCompany.valueOf(request.getFlightCompany()))
                .min_passengers(request.getMin_passengers())
                .max_passengers(request.getMax_passengers())
                .status(TripStatus.valueOf(request.getStatus()))
                .tripServices(services)
                .price(tripPrice)
                .isPrivate(request.getIsPrivate())
                .build();

        List<Hotel> hotels = new ArrayList<>();
        if (request.getHotels() != null) {
            for(Integer hotel : request.getHotels()){
                hotels.add(hotelRepository.findById(hotel).orElseThrow(
                        ()-> new RequestNotValidException("Hotel not found")
                ));
            }
//            totalPrice+= request.getHotelPrice();
            trip.setHotel(hotels);
            tripPrice.setHotelPrice(tripPrice.getHotelPrice());
            tripPriceRepository.save(tripPrice);
            trip.setPrice(tripPrice);
        }

        tripRepository.save(trip);

        TripResponse response = TripResponse.builder()
                .tripId(trip.getId())
                .tripName(trip.getName())
                .tripDescription(trip.getDescription())
                .tripCategory(trip.getTripCategory())
                .tripStartDate(trip.getStartDate())
                .tripEndDate(trip.getEndDate())
                .country(trip.getCountry().getName())
                .cities(trip.getCities().stream().map(City::getName).toList())
//                .hotels(trip.getHotel().stream().map(Hotel::getName).toList())
                .flightCompany(trip.getFlightCompany())
                .min_passengers(trip.getMin_passengers())
                .max_passengers(trip.getMax_passengers())
                .status(trip.getStatus())
                .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                .price(totalPrice)
                .isPrivate(isPrivateChecker(trip.getIsPrivate()))
                .build();
        if(!hotels.isEmpty()){
            response.setHotels(Optional.of( trip.getHotel().stream().map(Hotel::getName).toList()));
        }

        return new ApiResponseClass("Create trip successfully", HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    @Transactional
    public ApiResponseClass updateTrip(Integer id, TripRequest request) {
        tripRequestValidator.validate(request);

        Trip trip = tripRepository.findById(id).orElseThrow(
                () -> new RequestNotValidException("Id not found"));

        List<City> cities = new ArrayList<>();
        for (Integer city : request.getCities()) {
            cities.add(cityRepository.findById(city).orElseThrow(
                    () -> new RequestNotValidException("City not found")
            ));
        }
        List<Hotel> hotels = new ArrayList<>();
        if(request.getHotels() != null){
            for(Integer hotel : request.getHotels()){
                hotels.add(hotelRepository.findById(hotel).orElseThrow(
                        ()-> new RequestNotValidException("Hotel not found")
                ));
            }
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
        if(request.getHotels() != null){
            tripPrice.setHotelPrice(request.getHotelPrice().get());
            tripPriceRepository.save(tripPrice);
//            trip.setPrice(tripPrice);
        }
        tripPrice.setServicesPrice(request.getServicesPrice());
        tripPriceRepository.save(tripPrice);

        Integer totalPrice =
                request.getFlightPrice() + request.getServicesPrice();


        trip.setName(request.getTripName());
        trip.setDescription(request.getTripDescription());
        trip.setTripCategory(TripCategory.valueOf(request.getTripCategory()));
        trip.setFlightCompany(FlightCompany.valueOf(request.getFlightCompany()));
        trip.setMin_passengers(request.getMin_passengers());
        trip.setMax_passengers(request.getMax_passengers());
        trip.setStatus(TripStatus.valueOf(request.getStatus()));
        trip.setStartDate(request.getTripStartDate());
        trip.setEndDate(request.getTripEndDate());
        trip.setCountry(countryRepository.findById(request.getCountry()).orElseThrow(
                ()-> new RequestNotValidException("Country not found")
        ));

        trip.setCities(cities);
        if(!hotels.isEmpty()){
//            totalPrice+= request.getHotelPrice();
            trip.setHotel(hotels);
            tripPrice.setHotelPrice(request.getHotelPrice().get());
            tripPriceRepository.save(tripPrice);
            trip.setPrice(tripPrice);
        }
        trip.setTripServices(services);
        trip.setPrice(tripPrice);
        trip.setIsPrivate(request.getIsPrivate());

        TripResponse response = TripResponse.builder()
                .tripId(trip.getId())
                .tripName(trip.getName())
                .tripDescription(trip.getDescription())
                .tripCategory(trip.getTripCategory())
                .tripStartDate(trip.getStartDate())
                .tripEndDate(trip.getEndDate())
                .country(trip.getCountry().getName())
                .cities(trip.getCities().stream().map(City::getName).toList())
//                .hotels(trip.getHotel().stream().map(Hotel::getName).toList())
                .flightCompany(trip.getFlightCompany())
                .min_passengers(trip.getMin_passengers())
                .max_passengers(trip.getMax_passengers())
                .status(trip.getStatus())
                .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                .price(totalPrice)
                .isPrivate(isPrivateChecker(trip.getIsPrivate()))
                .build();
        if(!hotels.isEmpty()){
            response.setHotels(Optional.of( trip.getHotel().stream().map(Hotel::getName).toList()));
        }

        return new ApiResponseClass("Update trip successfully", HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    public ApiResponseClass deleteTrip(Integer id) {
        Trip trip = tripRepository.findById(id).orElseThrow(
                ()->new RequestNotValidException("Id not found")
        );
        tripRepository.delete(trip);
         return new ApiResponseClass("Delete trip successfully", HttpStatus.ACCEPTED , LocalDateTime.now());

    }

//    public ApiResponseClass searchTripsByCategory(String searchTerm){
////        List<Trip> trips = tripRepository.findByTripCategory(TripCategory.valueOf(tripCategory));
//        TripSpecification specification = new TripSpecification(searchTerm);
//        List<Trip> trips = tripRepository.findAll(specification);
//        List<TripResponse> responses = new ArrayList<>();
//        for(Trip trip : trips){
//            responses.add(TripResponse.builder()
//                    .tripId(trip.getId())
//                    .tripName(trip.getName())
//                    .tripDescription(trip.getDescription())
//                    .tripCategory(trip.getTripCategory())
//                    .tripStartDate(trip.getStartDate())
//                    .tripEndDate(trip.getEndDate())
//                    .country(trip.getCountry().getName())
//                    .cities(trip.getCities().stream().map(City::getName).toList())
////                .hotels(trip.getHotel().stream().map(Hotel::getName).toList())
//                    .flightCompany(trip.getFlightCompany())
//                    .min_passengers(trip.getMin_passengers())
//                    .max_passengers(trip.getMax_passengers())
//                    .status(trip.getStatus())
//                    .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
////                    .price(totalPrice)
////                    .isPrivate(isPrivate)
//                    .build());
//        }
//    }

    public ApiResponseClass GetByCategory(FilterTripByCategoryRequest request){
        filterTripByCategoryRequestValidator.validate(request);
        List<Trip> tripList = tripRepository.findByTripCategory(TripCategory.valueOf(request.getCategory()));

        List<TripResponse> tripResponseList = new ArrayList<>();

        if(tripList.isEmpty()){
            throw new RequestNotValidException("No trips in this trip category");
        }
        for(Trip trip : tripList){

            if(trip.getPrice()== null){
                throw new RequestNotValidException("Price not found");
            }
            Integer totalPrice = totalPriceCalculator(trip.getPrice().getServicesPrice() ,
                    trip.getPrice().getFlightPrice(),
                    Optional.empty());
            List<String> hotelList = new ArrayList<>();
            if(!trip.getHotel().isEmpty()) {
                totalPrice+= trip.getPrice().getHotelPrice();
                hotelList = trip.getHotel().stream().map(Hotel::getName).toList();
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
                    .hotels(Optional.of(hotelList))
                    .flightCompany(trip.getFlightCompany())
                    .min_passengers(trip.getMin_passengers())
                    .max_passengers(trip.getMax_passengers())
                    .status(trip.getStatus())
                    .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                    .price(totalPrice)
                    .isPrivate(isPrivateChecker(trip.getIsPrivate()))
                    .build());
        }

        return new ApiResponseClass("Get All trips by category done successfully", HttpStatus.ACCEPTED, LocalDateTime.now(),tripResponseList) ;

    }



}
