package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/favourite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;



    @PostMapping("add_hotel_to_favorite")
    public ApiResponseClass addHotelToFavorite(@RequestBody FavoriteRequest request) {
        return favoriteService.addHotelToFavourite(request);
    }

    @PostMapping("add_city_to_favorite")
    public ApiResponseClass addCityToFavorite(@RequestBody FavoriteRequest request) {
        return favoriteService.addCityToFavourite(request);
    }
    @PostMapping("add_place_to_favorite")
    public ApiResponseClass addPlaceToFavorite(@RequestBody FavoriteRequest request) {
        return favoriteService.addPlaceToFavourite(request);
    }

//    @DeleteMapping("delete_hotel_from_favorite")
//    public ApiResponseClass deleteHotelFromFavorite(@RequestBody FavoriteRequest request) {
//        return favoriteService.removeHotelFromFavourite(request);
//    }
//
//    @DeleteMapping("delete_city_from_favorite")
//    public ApiResponseClass deleteCityFromFavorite(@RequestBody FavoriteRequest request) {
//        return favoriteService.removeCityFromFavourite(request);
//    }
//    @DeleteMapping("delete_place_from_favorite")
//    public ApiResponseClass deletePlaceFromFavorite(@RequestBody FavoriteRequest request) {
//        return favoriteService.removePlaceFromFavourite(request);
//    }
    @DeleteMapping("delete_hotel_from_favorite/{id}")
    public ApiResponseClass deleteFromFavorite(@PathVariable Integer id) {
        return favoriteService.removeFromFavourite(id);
    }


}
