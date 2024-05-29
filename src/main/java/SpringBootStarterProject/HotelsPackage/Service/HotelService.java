package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.City_Place_Package.Repository.CityRepository;
import SpringBootStarterProject.City_Place_Package.Repository.CountryRepository;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ResourcesPackage.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
    private final HotelDetailsRepository hotelDetailsRepository;

    public ApiResponseClass findHotelById(Integer id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
        HotelResponse response = getHotelResponse(hotel);
        return new ApiResponseClass("Hotel get successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }


    public ApiResponseClass findHotelByCityId(Integer id) {
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
                    .num_of_rooms(hotel.getNum_of_rooms())
                    .description(hotel.getDescription())
                    .stars(hotel.getStars())
                    .build();
            response.add(oneHotelResponse);
        }

        return new ApiResponseClass("Hotels get successfully by city id", HttpStatus.OK, LocalDateTime.now(),response);
    }

    public ApiResponseClass findAllHotels() {

        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelResponse> response =new ArrayList<>();
        for(Hotel hotel : hotels) {
            HotelResponse oneHotelResponse = HotelResponse.builder()
                    .hotelId(hotel.getId())
                    .hotelName(hotel.getName())
                    .city(hotel.getCity())
                    .country(hotel.getCountry())
                    .photo(fileService.getFile(hotel.getPhotoId()))
                    .num_of_rooms(hotel.getNum_of_rooms())
                    .description(hotel.getDescription())
                    .stars(hotel.getStars())
                    .build();
            response.add(oneHotelResponse);
        }

        return new ApiResponseClass("All Hotels get successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }

    public ApiResponseClass findHotelByCountryId(Integer id) {
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
                    .num_of_rooms(hotel.getNum_of_rooms())
                    .description(hotel.getDescription())
                    .stars(hotel.getStars())
                    .build();
            response.add(oneHotelResponse);
        }

        return new ApiResponseClass("Hotels get successfully by country-id", HttpStatus.OK, LocalDateTime.now(),response);
    }

    public ApiResponseClass save(HotelRequest request) {

        newHotelValidator.validate(request);

        City city = cityRepository.findByName(request.getCity()).orElseThrow(
                () -> new RequestNotValidException("City not found")
        );
        Country country = countryRepository.findByName(request.getCountry()).orElseThrow(
                () -> new RequestNotValidException("Country not found")
        );

        if(city.getCountry() != country) throw new RequestNotValidException("City does not match country");

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

        HotelResponse response = getHotelResponse(savedHotel);
        return new ApiResponseClass("Hotel Created successfully", HttpStatus.CREATED, LocalDateTime.now(),response);

    }

    public ApiResponseClass update(Integer hotelId, @NotNull HotelRequest request){
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
        HotelResponse response = getHotelResponse(hotelToUpdate);
        return new ApiResponseClass("Hotel Updated successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }

    public ApiResponseClass delete(@NonNull Integer hotelId, @NotNull HotelRequest request) {
        Hotel hotel=hotelRepository.findById(hotelId).orElseThrow(() -> new RequestNotValidException("Hotel not found"));
        hotelRepository.delete(hotel);

        return new ApiResponseClass("Hotel Deleted successfully", HttpStatus.OK, LocalDateTime.now());
    }

    public HotelResponse getHotelResponse(@NotNull Hotel hotel) {
        return HotelResponse.builder()
                .hotelId(hotel.getId())
                .hotelName(hotel.getName())
                .city(hotel.getCity())
                .country(hotel.getCountry())
                .photo(fileService.getFile(hotel.getPhotoId()))
                .num_of_rooms(hotel.getNum_of_rooms())
                .description(hotel.getDescription())
                .stars(hotel.getStars())
                .build();
    }


    public ApiResponseClass findHotelsBetweenTwoAvgRating(Double avgAfter,Double avgBefore){


        if(avgAfter > avgBefore){
            return new ApiResponseClass("Minimum-Rating should be less than Maximum-Rating", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }

        List<HotelDetails> details = hotelDetailsRepository.findHotelDetailsByAverageRatingBetween(avgBefore,avgAfter);
        if(details.isEmpty()) return  new ApiResponseClass("No Hotels Found", HttpStatus.OK, LocalDateTime.now());

        List<Hotel> hotels = new ArrayList<>();
        for(HotelDetails detail : details){
            hotels.add(hotelRepository.findById(detail.getId()).orElseThrow(()-> new RequestNotValidException("Hotel not found")));
        }
        List<HotelResponse> responses = new ArrayList<>();
        for(Hotel hotel : hotels){
            responses.add(getHotelResponse(hotel));
        }
        return new ApiResponseClass("Hotels Found", HttpStatus.OK, LocalDateTime.now(), responses);





    }



}
