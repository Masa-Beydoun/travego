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

    @PostMapping("add_place_to_favorite")
    public ApiResponseClass addPlaceToFavorite(@RequestBody FavoriteRequest request) {
        return favoriteService.addPlaceToFavourite(request);
    }

    @DeleteMapping("delete_from_favorite/{id}")
    public ApiResponseClass deleteFromFavorite(@PathVariable Integer id) {
        return favoriteService.removeFromFavourite(id);
    }


}
