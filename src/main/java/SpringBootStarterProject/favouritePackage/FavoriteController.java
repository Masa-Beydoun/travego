package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
