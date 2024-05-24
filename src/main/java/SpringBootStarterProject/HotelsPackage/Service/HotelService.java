package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.City_Place_Package.Repository.CityRepository;
import SpringBootStarterProject.City_Place_Package.Repository.CountryRepository;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ResourcesPackage.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final FileService fileService;

    private final ObjectsValidator<HotelRequest> newHotelValidator;

    public HotelResponse findHotelById(Integer id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
        return getHotelResponse(hotel);
    }


    public List<HotelResponse> findHotelByCityId(Integer id) {
        City city = cityRepository.findById(id).orElseThrow(()-> new RequestNotValidException("City not found"));
        List<Hotel> hotels = hotelRepository.findAllByCityId(city);
        if(hotels.isEmpty()) throw new RequestNotValidException("No Hotels found");
        List<HotelResponse> response =new ArrayList<>();
        for(Hotel hotel : hotels) {
            HotelResponse oneHotelResponse = HotelResponse.builder()
                    .hotelId(hotel.getId())
                    .hotelName(hotel.getName())
                    .city(hotel.getCity())
                    .country(hotel.getCountry())
                    .photo(fileService.getFile(hotel.getPhotoId()))

                    .hotelDetails(hotel.getHotelDetails())
                    .num_of_rooms(hotel.getNum_of_rooms())
                    .description(hotel.getDescription())
                    .stars(hotel.getStars())
                    .build();
            response.add(oneHotelResponse);
        }

        return response;
    }

    public List<HotelResponse> findAllHotels() {

        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelResponse> response =new ArrayList<>();
        for(Hotel hotel : hotels) {
            HotelResponse oneHotelResponse = HotelResponse.builder()
                    .hotelId(hotel.getId())
                    .hotelName(hotel.getName())
                    .city(hotel.getCity())
                    .country(hotel.getCountry())
                    .photo(fileService.getFile(hotel.getPhotoId()))
                    .hotelDetails(hotel.getHotelDetails())
                    .num_of_rooms(hotel.getNum_of_rooms())
                    .description(hotel.getDescription())
                    .stars(hotel.getStars())
                    .build();
            response.add(oneHotelResponse);
        }

        return response;
    }

    public List<HotelResponse> findHotelByCountryId(Integer id) {
        Country country = countryRepository.findById(id).orElseThrow(()-> new RuntimeException("City not found"));
        if(country == null) throw new RequestNotValidException("City not found");
        List<Hotel> hotels = hotelRepository.findAllByCountryId(country);
        if(hotels.isEmpty()) throw new RuntimeException("No Hotels found");
        List<HotelResponse> response =new ArrayList<>();
        for(Hotel hotel : hotels) {
            HotelResponse oneHotelResponse = HotelResponse.builder()
                    .hotelId(hotel.getId())
                    .hotelName(hotel.getName())
                    .city(hotel.getCity())
                    .country(hotel.getCountry())
                    .photo(fileService.getFile(hotel.getPhotoId()))
                    .hotelDetails(hotel.getHotelDetails())
                    .num_of_rooms(hotel.getNum_of_rooms())
                    .description(hotel.getDescription())
                    .stars(hotel.getStars())
                    .build();
            response.add(oneHotelResponse);
        }

        return response;
    }

    public HotelResponse save(HotelRequest request) {

        newHotelValidator.validate(request);
        City city = cityRepository.findByName(request.getCity()).orElseThrow(
                () -> new RequestNotValidException("City not found")
        );
        Country country = countryRepository.findByName(request.getCountry()).orElseThrow(
                () -> new RequestNotValidException("Country not found")
        );


        if(request.getFile().isEmpty()) throw new RequestNotValidException("Photo not found");
        FileEntity savedPhoto =fileService.saveFile(request.getFile());


        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .city(city)
                .country(country)
                .photoId(savedPhoto.getId())
                .num_of_rooms(request.getNum_of_rooms())
                .stars(request.getStars())
                .build();
        Hotel savedHotel = hotelRepository.save(hotel);
        fileService.update(savedPhoto,ResourceType.HOTEL, savedHotel.getId());

        return getHotelResponse(savedHotel);
    }

    public Hotel updateWithDetails(@NotNull HotelDetails hotelDetails) {
        Hotel hotel = hotelRepository.findById(hotelDetails.getHotel().getId()).orElseThrow(() -> new RequestNotValidException("Hotel not found"));
        hotel.setHotelDetails(hotelDetails);
        return hotel;
    }

    public HotelResponse update(Integer hotelId,HotelRequest request){
        Hotel hotelToUpdate = hotelRepository.findById(hotelId).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
        hotelToUpdate.setName(request.getName());
        hotelToUpdate.setDescription(request.getDescription());
        City city = cityRepository.findByName(request.getCity()).orElseThrow(()-> new RequestNotValidException("City not found"));
        hotelToUpdate.setCity(city);
        Country country = countryRepository.findByName(request.getCountry()).orElseThrow(()-> new RequestNotValidException("Country not found"));
        hotelToUpdate.setCountry(country);

//        hotelToUpdate.setPhotoId();

        hotelToUpdate.setNum_of_rooms(request.getNum_of_rooms());
        hotelToUpdate.setStars(request.getStars());
        return getHotelResponse(hotelToUpdate);
    }

    public void delete(@NonNull Integer hotelId, @NotNull HotelRequest request) {
        Hotel hotel=hotelRepository.findById(hotelId).orElseThrow(() -> new RequestNotValidException("Hotel not found"));
        hotelRepository.delete(hotel);
    }


    public HotelResponse getHotelResponse(Hotel hotel) {
        return HotelResponse.builder()
                .hotelId(hotel.getId())
                .hotelName(hotel.getName())
                .city(hotel.getCity())
                .country(hotel.getCountry())
                .photo(fileService.getFile(hotel.getPhotoId()))
                .hotelDetails(hotel.getHotelDetails())
                .num_of_rooms(hotel.getNum_of_rooms())
                .description(hotel.getDescription())
                .stars(hotel.getStars())
                .build();
    }


}
