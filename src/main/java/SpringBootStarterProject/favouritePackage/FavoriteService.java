package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.HotelsPackage.Service.HotelService;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Utils.UtilsService;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trippackage.Models.Trip;
import SpringBootStarterProject.Trippackage.Repository.TripRepository;
import SpringBootStarterProject.Trippackage.Response.TripResponseForClient;
import SpringBootStarterProject.Trippackage.Service.TripService;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ObjectsValidator<FavoriteRequest> validator;
    private final ClientRepository clientRepository;
    private final HotelRepository hotelRepository;
    private final TripRepository tripRepository;
    private final HotelService hotelService;
    private final TripService tripService;
    @Autowired
    private UtilsService utilsService;

    public FavouriteTripResponseForClient extractToFavouriteTripResponse(TripResponseForClient data){
        FavouriteTripResponseForClient response = FavouriteTripResponseForClient.builder()
                .tripId(data.getTripId())
                .tripName(data.getTripName())
                .tripDescription(data.getTripDescription())
                .tripCategory(data.getTripCategory())
                .tripStartDate(data.getTripStartDate())
                .tripEndDate(data.getTripEndDate())
                .country(data.getCountry())
                .cities(data.getCities())
                .hotels(data.getHotels())
                .flightCompany(data.getFlightCompany())
                .min_passengers(data.getMin_passengers())
                .max_passengers(data.getMax_passengers())
                .status(data.getStatus())
                .tripServices(data.getTripServices())
                .price(data.getPrice())
                .isPrivate(data.getIsPrivate())
                .isFavourite(data.getIsFavourite())
                .build();
        return response;
    }

    public FavouriteHotelResponse extractToFavouriteHotelResponse(HotelResponse data){
        FavouriteHotelResponse response = FavouriteHotelResponse.builder()
                .hotelId(data.getHotelId())
                .hotelName(data.getHotelName())
                .cityId(data.getCityId())
                .cityName(data.getCityName())
                .country(data.getCountry())
                .countryId(data.getCountryId())
                .photo(data.getPhoto())
                .description(data.getDescription())
                .stars(data.getStars())
                .build();
        return response;
    }

    @Transactional
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


    @Transactional
    public ApiResponseClass addTripToFavourite(FavoriteRequest request) {
        validator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));


        Trip place = tripRepository.findById(request.getFavouriteId()).orElseThrow(
                ()-> new RequestNotValidException("Trip not found"));
        Favorite existedFavorite = favoriteRepository.findByClientIdAndFavouriteIdAndFavoriteType(
                client.getId(), request.getFavouriteId(), FavoriteType.TRIP)
                .orElse(null);
        if(existedFavorite != null) {
            throw new RequestNotValidException("Trip already exists");
        }
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


        Favorite favorite = favoriteRepository.findById(id).orElseThrow(
                ()->new RequestNotValidException("favorite not found"));
        favoriteRepository.delete(favorite);
        return new ApiResponseClass("Removed from favorites successfully", HttpStatus.OK, LocalDateTime.now());

    }

    public ApiResponseClass getAllMyFavourites(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication.getName() == null) {
//            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
//        }

        var client = utilsService.extractCurrentUser();

        List<Favorite> favoriteList = favoriteRepository.findByClientId(client.getId()).orElse(null);
        if(favoriteList == null){
            return new ApiResponseClass("No Favourites found", HttpStatus.OK, LocalDateTime.now());
        }
        List<TripResponseForClient> tripResponses=new ArrayList<>();
        List<FavouriteTripResponseForClient > responseForClientList = new ArrayList<>();
        List<FavouriteHotelResponse> responseHotelList = new ArrayList<>();
        for(Favorite favorite : favoriteList){
            if(favorite.getFavoriteType().equals(FavoriteType.HOTEL)){
                Hotel hotel = hotelRepository.findById(favorite.getFavouriteId()).orElse(null);
                if(hotel == null){
                    continue;
                }
                HotelResponse firstResponse = hotelService.getHotelResponse(hotel);
                FavouriteHotelResponse response = extractToFavouriteHotelResponse(firstResponse);
                response.setFavouriteHotelId(favorite.getId());
                responseHotelList.add(response);
            }
            else{
                Trip trip = tripRepository.findById(favorite.getFavouriteId()).orElse(null);
                if(trip == null){
                    continue;
                }
                Integer totalPrice = tripService.totalPriceCalculator(trip.getPrice().getServicesPrice(),
                        trip.getPrice().getFlightPrice(),
                        Optional.empty());
                List<String> hotelList = new ArrayList<>();
                if(!trip.getHotel().isEmpty()) {
                    totalPrice += trip.getPrice().getHotelPrice();
                    hotelList = trip.getHotel().stream().map(Hotel::getName).toList();
                }
                TripResponseForClient firstTypeResponse = tripService.extractToResponse(trip);
                firstTypeResponse.setPrice(totalPrice);
                firstTypeResponse.setHotels(Optional.of(hotelList));

                if(client != null && client instanceof Client){
                    firstTypeResponse.setIsFavourite(tripService.isFavouriteForClientChecker(trip));
                }
            FavouriteTripResponseForClient responseForClient = this.extractToFavouriteTripResponse(firstTypeResponse);
                responseForClient.setFavouriteTripId(favorite.getId());
                responseForClientList.add(responseForClient);
            }
        }
        FavoriteResponse responses= FavoriteResponse.builder()
                .hotels(responseHotelList)
                .trips(responseForClientList)
                .build();
        return new ApiResponseClass("All favourite got successfully", HttpStatus.OK, LocalDateTime.now(), responses);
    }
}
