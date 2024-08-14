package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.ResourcesPackage.Response.FileMetaDataResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavouriteHotelResponse {

    private Integer favouriteHotelId;
    private Integer hotelId;
    private String hotelName;
    private Integer num_of_rooms;
    private Integer stars;
    private Integer cityId;
    private String cityName;
    private Integer countryId;
    private String country;
    private String description;
    private FileMetaDataResponse photo;
}
