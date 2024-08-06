package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.Trippackage.Response.TripResponse;
import SpringBootStarterProject.UserPackage.Models.Client;
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
    private List<HotelResponse> hotels;
    private List<TripResponse> trips;


}
