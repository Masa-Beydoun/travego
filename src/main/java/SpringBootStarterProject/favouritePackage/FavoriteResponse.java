package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.Trippackage.Response.TripResponseForClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteResponse {
    private List<FavouriteHotelResponse> hotels;
    private List<FavouriteTripResponseForClient> trips;


}
