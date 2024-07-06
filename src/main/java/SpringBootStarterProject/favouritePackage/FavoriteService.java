package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.City_Place_Package.Repository.CityRepository;
import SpringBootStarterProject.City_Place_Package.Repository.PlaceRepository;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ObjectsValidator<FavoriteRequest> validator;
    private final ClientRepository clientRepository;
    private final HotelRepository hotelRepository;
    private final PlaceRepository placeRepository;

    public ApiResponseClass addHotelToFavourite(FavoriteRequest request) {
        validator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Favorite favorite;
        Hotel hotel = hotelRepository.findById(request.getFavouriteId()).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
        favorite = Favorite.builder()
                .favouriteId(hotel.getId())
                .favoriteType(FavoriteType.HOTEL)
                .client(client)
                .build();
        favoriteRepository.save(favorite);
        FavoriteResponse response = FavoriteResponse.builder()
                .id(favorite.getId())
                .client(favorite.getClient())
                .favouriteType(favorite.getFavoriteType().name())
                .favouriteId(favorite.getFavouriteId())
                .build();
        return new ApiResponseClass("Hotel Added to favorites successfully", HttpStatus.CREATED, LocalDateTime.now(),response);
    }


    public ApiResponseClass addPlaceToFavourite(FavoriteRequest request) {
        validator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Favorite favorite;
        Place place = placeRepository.findById(request.getFavouriteId()).orElseThrow(()-> new RequestNotValidException("Place not found"));
        favorite = Favorite.builder()
                .favouriteId(place.getId())
                .favoriteType(FavoriteType.PLACE)
                .client(client)
                .build();
        favoriteRepository.save(favorite);
        FavoriteResponse response = FavoriteResponse.builder()
                .id(favorite.getId())
                .client(favorite.getClient())
                .favouriteType(favorite.getFavoriteType().name())
                .favouriteId(favorite.getFavouriteId())
                .build();
        return new ApiResponseClass("Place Added to favorites successfully", HttpStatus.CREATED, LocalDateTime.now(),response);
    }


    public ApiResponseClass removeFromFavourite(Integer id) {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(()->new RequestNotValidException("favorite not found"));
        favoriteRepository.delete(favorite);
        return new ApiResponseClass("Hotel removed from favorites successfully", HttpStatus.CREATED, LocalDateTime.now());

    }
}
