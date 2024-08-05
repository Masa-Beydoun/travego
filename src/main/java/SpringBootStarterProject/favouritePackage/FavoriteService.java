package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.HotelsPackage.Service.HotelService;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.Trip_package.Models.TripServices;
import SpringBootStarterProject.Trip_package.Repository.TripRepository;
import SpringBootStarterProject.Trip_package.Response.TripResponse;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
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
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ObjectsValidator<FavoriteRequest> validator;
    private final ClientRepository clientRepository;
    private final HotelRepository hotelRepository;
    private final TripRepository tripRepository;
    private final HotelService hotelService;

    public ApiResponseClass addHotelToFavourite(FavoriteRequest request) {
        validator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now());
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));


        Favorite favorite;
        Hotel hotel = hotelRepository.findById(request.getFavouriteId()).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
        favorite = Favorite.builder()
                .favouriteId(hotel.getId())
                .favoriteType(FavoriteType.HOTEL)
                .client(client)
                .build();
        favoriteRepository.save(favorite);

        return new ApiResponseClass("Hotel Added to favorites successfully", HttpStatus.CREATED, LocalDateTime.now());
    }


    public ApiResponseClass addTripToFavourite(FavoriteRequest request) {
        validator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));


        Trip place = tripRepository.findById(request.getFavouriteId()).orElseThrow(()-> new RequestNotValidException("Trip not found"));
        Favorite favorite = Favorite.builder()
                .favouriteId(place.getId())
                .favoriteType(FavoriteType.TRIP)
                .client(client)
                .build();
        favoriteRepository.save(favorite);

        return new ApiResponseClass("Trip Added to favorites successfully", HttpStatus.CREATED, LocalDateTime.now());
    }


    public ApiResponseClass removeFromFavourite(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));


        Favorite favorite = favoriteRepository.findById(id).orElseThrow(()->new RequestNotValidException("favorite not found"));
        favoriteRepository.delete(favorite);
        return new ApiResponseClass("Removed from favorites successfully", HttpStatus.OK, LocalDateTime.now());

    }

    public ApiResponseClass getAllMyFavourites(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));


        List<Favorite> favoriteList = favoriteRepository.findByClientId(client.getId()).orElse(null);
        if(favoriteList == null){
            return new ApiResponseClass("No Favourites found", HttpStatus.OK, LocalDateTime.now());
        }
        List<HotelResponse> hotelResponses=new ArrayList<>();
        List<TripResponse> tripResponses=new ArrayList<>();
        for(Favorite favorite : favoriteList){
            if(favorite.getFavoriteType().equals(FavoriteType.HOTEL)){
                Hotel hotel = hotelRepository.findById(favorite.getId()).orElse(null);
                if(hotel == null){
                    continue;
                }
                HotelResponse response = hotelService.getHotelResponse(hotel);
                hotelResponses.add(response);
            }
            else{
                Trip trip = tripRepository.findById(favorite.getId()).orElse(null);
                if(trip == null){
                    continue;
                }
                Integer totalPrice =
                        trip.getPrice().getFlightPrice()
                                + trip.getPrice().getHotelPrice()
                                + trip.getPrice().getServicesPrice();
                String isPrivate = "public";
                if(trip.getIsPrivate()){
                    isPrivate = "private";
                }
                List<String> hotelList = new ArrayList<>();
                if(!trip.getHotel().isEmpty()) {
                    totalPrice += trip.getPrice().getHotelPrice();
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
                        .hotels(hotelList)
                        .flightCompany(trip.getFlightCompany())
                        .min_passengers(trip.getMin_passengers())
                        .max_passengers(trip.getMax_passengers())
                        .status(trip.getStatus())
                        .tripServices(trip.getTripServices().stream().map(TripServices::getName).toList())
                        .price(totalPrice)
                        .isPrivate(isPrivate)
                        .build();

                tripResponses.add(response);
            }

        }

        FavoriteResponse responses= FavoriteResponse.builder()
                .hotels(hotelResponses)
                .trips(tripResponses)
                .build();
        return new ApiResponseClass("All favourite got successfully", HttpStatus.OK, LocalDateTime.now(), responses);

    }
}
