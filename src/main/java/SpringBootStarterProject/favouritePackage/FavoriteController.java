package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/favourite")
@RequiredArgsConstructor
@Tag(name = "Favourite Controller")
public class FavoriteController {

    private final FavoriteService favoriteService;



    @PostMapping("add_hotel_to_favorite")
    public ApiResponseClass addHotelToFavorite(@RequestBody FavoriteRequest request) {
        return favoriteService.addHotelToFavourite(request);
    }

    @PostMapping("add_trip_to_favorite")
    public ApiResponseClass addTripToFavorite(@RequestBody FavoriteRequest request) {
        return favoriteService.addTripToFavourite(request);
    }

    @DeleteMapping("remove_from_favorite/{id}")
    public ApiResponseClass removeFromFavorite(@PathVariable Integer id) {
        return favoriteService.removeFromFavourite(id);
    }

    @GetMapping("all_my_favourites")
    public ApiResponseClass allMyFavourites() {
        return favoriteService.getAllMyFavourites();
    }

    @DeleteMapping("by-hotel/{id}")
    public ApiResponseClass deleteByHotel(@PathVariable Integer id) {
        return favoriteService.removeFromFavoriteByHotelId(id);
    }
    @DeleteMapping("by-trip/{id}")
    public ApiResponseClass deleteByTrip(@PathVariable Integer id) {
        return favoriteService.removeFromFavoriteByTripId(id);
    }

}
