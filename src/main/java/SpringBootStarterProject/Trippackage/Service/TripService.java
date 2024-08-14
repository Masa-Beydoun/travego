package SpringBootStarterProject.Trippackage.Service;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Repository.CityRepository;
import SpringBootStarterProject.City_Place_Package.Repository.CountryRepository;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Utils.UtilsService;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
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
import SpringBootStarterProject.Trippackage.Request.FilterTripByStatusRequest;
import SpringBootStarterProject.Trippackage.Request.TripRequest;
import SpringBootStarterProject.Trippackage.Response.TripResponseForClient;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import SpringBootStarterProject.favouritePackage.Favorite;
import SpringBootStarterProject.favouritePackage.FavoriteRepository;
import SpringBootStarterProject.favouritePackage.FavoriteType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private ClientRepository clientRepository;

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private ObjectsValidator<FilterTripByCategoryRequest> filterTripByCategoryRequestValidator;
    @Autowired
    private ObjectsValidator<TripRequest> tripRequestValidator;
    @Autowired
    private ObjectsValidator<FilterTripByStatusRequest> filterTripByStatusRequestValidator;


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

    @Transactional
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

    public Boolean isFavouriteForClientChecker(Trip trip){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found"));
        Optional<Favorite> favorite = favoriteRepository.findByClientIdAndFavouriteIdAndFavoriteType(client.getId(), trip.getId(), FavoriteType.TRIP);
        return favorite.isPresent();
    }
    public Boolean haveHotelsChecker(TripRequest tripRequest){
        if(tripRequest.getHotels() != null){
            return true;
        }
        return false;
    }

    public TripResponseForClient extractToResponse(Trip trip){
            return  TripResponseForClient.builder()
                .tripId(trip.getId())
                .tripName(trip.getName())
                .tripDescription(trip.getDescription())
                .tripCategory(trip.getTripCategory())
                .tripStartDate(trip.getStartDate())
                .tripEndDate(trip.getEndDate())
                .country(trip.getCountry().getName())
                .cities(trip.getCities().stream().map(City::getName).toList())
//                .hotels(Optional.of(hotelList))
                .flightCompany(trip.getFlightCompany())
                .min_passengers(trip.getMin_passengers())
                .max_passengers(trip.getMax_passengers())
                .status(trip.getStatus())
                .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
//                .price(totalPrice)
                .isPrivate(isPrivateChecker(trip.getIsPrivate()))
                .isFavourite(isFavouriteForClientChecker(trip))
                .build();
    }
    public ApiResponseClass getTripsForClient() {
        List<Trip>  tripList =  tripRepository.findByIsPrivateFalse();
        List<TripResponseForClient> tripResponseList = new ArrayList<>();

        for(Trip trip : tripList){
            Integer totalPrice = totalPriceCalculator(trip.getPrice().getServicesPrice() ,
                    trip.getPrice().getFlightPrice(),
                    Optional.empty());
            List<String> hotelList = new ArrayList<>();
            if(!trip.getHotel().isEmpty()) {
                totalPrice+= trip.getPrice().getHotelPrice();
                hotelList = trip.getHotel().stream().map(Hotel::getName).toList();
            }
//                tripResponseList.add(TripResponseForClient.builder()
//                        .tripId(trip.getId())
//                        .tripName(trip.getName())
//                        .tripDescription(trip.getDescription())
//                        .tripCategory(trip.getTripCategory())
//                        .tripStartDate(trip.getStartDate())
//                        .tripEndDate(trip.getEndDate())
//                        .country(trip.getCountry().getName())
//                        .cities(trip.getCities().stream().map(City::getName).toList())
//                        .hotels(Optional.of(hotelList))
//                        .flightCompany(trip.getFlightCompany())
//                        .min_passengers(trip.getMin_passengers())
//                        .max_passengers(trip.getMax_passengers())
//                        .status(trip.getStatus())
//                        .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
//                        .price(totalPrice)
//                        .isPrivate(isPrivateChecker(trip.getIsPrivate()))
//                        .isFavourite(isFavouriteForClientChecker(trip))
//                        .build());
            tripResponseList.add(extractToResponse(trip));
            tripResponseList.get(tripResponseList.size()-1).setPrice(totalPrice);
            tripResponseList.get(tripResponseList.size()-1).setHotels(Optional.of(hotelList));

            var client = utilsService.extractCurrentUser();
            if(client != null && client instanceof Client){
                tripResponseList.get(tripResponseList.size()-1).setIsFavourite(isFavouriteForClientChecker(trip));
            }
        }
        return new ApiResponseClass("Get All trips done successfully", HttpStatus.ACCEPTED, LocalDateTime.now(),tripResponseList) ;
    }


    public ApiResponseClass getAllTripsForAdmin(){
        List<Trip>  tripList =  tripRepository.findAll();
        List<TripResponseForClient> tripResponseList = new ArrayList<>();
        for(Trip trip : tripList){

            Integer totalPrice = totalPriceCalculator(trip.getPrice().getServicesPrice() ,
                    trip.getPrice().getFlightPrice(),
                    Optional.empty());
            List<String> hotelList = new ArrayList<>();
            if(!trip.getHotel().isEmpty()) {
                totalPrice+= trip.getPrice().getHotelPrice();
                hotelList = trip.getHotel().stream().map(Hotel::getName).toList();
            }
            tripResponseList.add(extractToResponse(trip));
            tripResponseList.get(tripResponseList.size()-1).setPrice(totalPrice);
            tripResponseList.get(tripResponseList.size()-1).setHotels(Optional.of(hotelList));
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
        TripResponseForClient response = extractToResponse(trip);
        response.setPrice(totalPrice);
        response.setHotels(Optional.of(hotelList));
        return new ApiResponseClass("Get trip by id done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }

    public ApiResponseClass getAllTripCategory(){
        List<TripCategory> tripCategories = List.of(TripCategory.values());
        return new ApiResponseClass("Get all categories" , HttpStatus.ACCEPTED, LocalDateTime.now(),tripCategories);
    }
    public ApiResponseClass getAllFlightCompany(){
        List<FlightCompany> flightCompanies = List.of(FlightCompany.values());
        return new ApiResponseClass("Get all flight companies" , HttpStatus.ACCEPTED, LocalDateTime.now(),flightCompanies);
    }
    public ApiResponseClass getAllTripStatus(){
        List<TripStatus> tripStatuses = List.of(TripStatus.values());
        return new ApiResponseClass("Get all status" , HttpStatus.ACCEPTED, LocalDateTime.now(),tripStatuses);
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
        List<String> hotelList  = new ArrayList<>();
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

            hotelList = hotels.stream().map(Hotel::getName).toList();;
        }

        tripRepository.save(trip);

        TripResponseForClient response = extractToResponse(trip);

        response.setPrice(totalPrice);
        if(!hotels.isEmpty()){
            response.setHotels(Optional.of( trip.getHotel().stream().map(Hotel::getName).toList()));
        }
        response.setHotels(Optional.of( hotelList));

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

        TripResponseForClient response = extractToResponse(trip);
//                TripResponse.builder()
//                .tripId(trip.getId())
//                .tripName(trip.getName())
//                .tripDescription(trip.getDescription())
//                .tripCategory(trip.getTripCategory())
//                .tripStartDate(trip.getStartDate())
//                .tripEndDate(trip.getEndDate())
//                .country(trip.getCountry().getName())
//                .cities(trip.getCities().stream().map(City::getName).toList())
////                .hotels(trip.getHotel().stream().map(Hotel::getName).toList())
//                .flightCompany(trip.getFlightCompany())
//                .min_passengers(trip.getMin_passengers())
//                .max_passengers(trip.getMax_passengers())
//                .status(trip.getStatus())
//                .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
//                .price(totalPrice)
//                .isPrivate(isPrivateChecker(trip.getIsPrivate()))
//                .build();
        response.setPrice(totalPrice);
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

    public ApiResponseClass getByCategory(FilterTripByCategoryRequest request){
        filterTripByCategoryRequestValidator.validate(request);
        List<Trip> tripList = tripRepository.findByTripCategory(TripCategory.valueOf(request.getCategory()));

        List<TripResponseForClient> tripResponseList = new ArrayList<>();

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
//            tripResponseList.add(TripResponse.builder()
//                    .tripId(trip.getId())
//                    .tripName(trip.getName())
//                    .tripDescription(trip.getDescription())
//                    .tripCategory(trip.getTripCategory())
//                    .tripStartDate(trip.getStartDate())
//                    .tripEndDate(trip.getEndDate())
//                    .country(trip.getCountry().getName())
//                    .cities(trip.getCities().stream().map(City::getName).toList())
//                    .hotels(Optional.of(hotelList))
//                    .flightCompany(trip.getFlightCompany())
//                    .min_passengers(trip.getMin_passengers())
//                    .max_passengers(trip.getMax_passengers())
//                    .status(trip.getStatus())
//                    .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
//                    .price(totalPrice)
//                    .isPrivate(isPrivateChecker(trip.getIsPrivate()))
//                    .build());
            tripResponseList.add(extractToResponse(trip));
            tripResponseList.get(tripResponseList.size()-1).setPrice(totalPrice);
            tripResponseList.get(tripResponseList.size()-1).setHotels(Optional.of(hotelList));
        }

        return new ApiResponseClass("Get All trips by category done successfully", HttpStatus.ACCEPTED, LocalDateTime.now(),tripResponseList) ;

    }

    public ApiResponseClass getByStatus(FilterTripByStatusRequest request){
        filterTripByStatusRequestValidator.validate(request);
        List<Trip> tripList = tripRepository.findByStatus(TripStatus.valueOf(request.getStatus()));

        List<TripResponseForClient> tripResponseList = new ArrayList<>();

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
//            tripResponseList.add(TripResponse.builder()
//                    .tripId(trip.getId())
//                    .tripName(trip.getName())
//                    .tripDescription(trip.getDescription())
//                    .tripCategory(trip.getTripCategory())
//                    .tripStartDate(trip.getStartDate())
//                    .tripEndDate(trip.getEndDate())
//                    .country(trip.getCountry().getName())
//                    .cities(trip.getCities().stream().map(City::getName).toList())
//                    .hotels(Optional.of(hotelList))
//                    .flightCompany(trip.getFlightCompany())
//                    .min_passengers(trip.getMin_passengers())
//                    .max_passengers(trip.getMax_passengers())
//                    .status(trip.getStatus())
//                    .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
//                    .price(totalPrice)
//                    .isPrivate(isPrivateChecker(trip.getIsPrivate()))
//                    .build());
            tripResponseList.add(extractToResponse(trip));
            tripResponseList.get(tripResponseList.size()-1).setPrice(totalPrice);
            tripResponseList.get(tripResponseList.size()-1).setHotels(Optional.of(hotelList));
        }

        return new ApiResponseClass("Get All trips by category done successfully", HttpStatus.ACCEPTED, LocalDateTime.now(),tripResponseList) ;

    }

    public ApiResponseClass searchByCharOfName(String searchTerm){
        List<Trip> tripList = tripRepository.findBySearchTerm(searchTerm);
        List<TripResponseForClient> responseList = new ArrayList<>();
        for(Trip trip : tripList){

            Integer totalPrice = totalPriceCalculator(trip.getPrice().getServicesPrice() ,
                    trip.getPrice().getFlightPrice(),
                    Optional.empty());
            List<String> hotelList = new ArrayList<>();
            if(!trip.getHotel().isEmpty()) {
                totalPrice+= trip.getPrice().getHotelPrice();
                hotelList = trip.getHotel().stream().map(Hotel::getName).toList();
            }
//            responseList.add(TripResponse.builder()
//                    .tripId(trip.getId())
//                    .tripName(trip.getName())
//                    .tripDescription(trip.getDescription())
//                    .tripCategory(trip.getTripCategory())
//                    .tripStartDate(trip.getStartDate())
//                    .tripEndDate(trip.getEndDate())
//                    .country(trip.getCountry().getName())
//                    .cities(trip.getCities().stream().map(City::getName).toList())
//                    .hotels(Optional.of(hotelList))
//                    .flightCompany(trip.getFlightCompany())
//                    .min_passengers(trip.getMin_passengers())
//                    .max_passengers(trip.getMax_passengers())
//                    .status(trip.getStatus())
//                    .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
//                    .price(totalPrice)
//                    .isPrivate(isPrivateChecker(trip.getIsPrivate()))
//                    .build());
            responseList.add(extractToResponse(trip));
            responseList.get(responseList.size()-1).setPrice(totalPrice);
            responseList.get(responseList.size()-1).setHotels(Optional.of(hotelList));
        }
        return new ApiResponseClass("Get all successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(),responseList);
    }


}
