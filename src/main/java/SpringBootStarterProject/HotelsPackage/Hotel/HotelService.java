package SpringBootStarterProject.HotelsPackage.Hotel;

import SpringBootStarterProject.City_package.Models.City;
import SpringBootStarterProject.City_package.Models.Country;
import SpringBootStarterProject.City_package.Repository.CityRepository;
import SpringBootStarterProject.City_package.Repository.CountryRepository;
import SpringBootStarterProject.HotelsPackage.HotelDetailsPackage.HotelDetails;
import SpringBootStarterProject.HotelsPackage.HotelServicesPackage.HotelServices;
import SpringBootStarterProject.HotelsPackage.HotelServicesPackage.HotelServicesRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelRequest;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ResourcesPackage.ResourceType;
import SpringBootStarterProject.ResourcesPackage.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final HotelServicesRepository hotelServicesRepository;
    private final FileService fileService;

    private final ObjectsValidator<HotelRequest> newHotelValidator;

    public Hotel findHotelById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
    }


    public List<Hotel> findHotelByCityId(Integer id) {
        City city = cityRepository.findById(id).orElseThrow(()-> new RequestNotValidException("City not found"));
        List<Hotel> hotels = hotelRepository.findAllByCityId(city);
        if(hotels.isEmpty()) throw new RequestNotValidException("No Hotels found");
        return hotels;
    }

    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    public List<Hotel> findHotelByCountryId(Integer id) {
        Country country = countryRepository.findById(id).orElseThrow(()-> new RuntimeException("City not found"));
        if(country == null) throw new RequestNotValidException("City not found");
        List<Hotel> hotels = hotelRepository.findAllByCountryId(country);
        if(hotels.isEmpty()) throw new RuntimeException("No Hotels found");
        return hotels;
    }

    public Hotel save(HotelRequest request) {

        newHotelValidator.validate(request);
        City city = cityRepository.findById(request.getCity().getId()).orElseThrow(
                () -> new RequestNotValidException("City not found")
        );
        Country country = countryRepository.findById(request.getCountry().getId()).orElseThrow(
                () -> new RequestNotValidException("Country not found")
        );

        List<HotelServices> services = new ArrayList<>();
        List<HotelServices> requestServices = request.getHotelServices();

        for (HotelServices service : requestServices) {
            services.add(hotelServicesRepository.findByName(service.getName()));
        }


        FileEntity savedPhoto = fileService.saveFile(request.getPhoto());


        Hotel hotel = Hotel.builder()
                .name(request.getName()) // done
                .description(request.getDescription())  //done
                .cityId(city) //done
                .countryId(country) //done
                .room(request.getRoom())
                .photos(savedPhoto) // done
                .num_of_rooms(request.getNum_of_rooms()) // done
                .stars(request.getStars()) // done
                .hotelServices(services)
                .build();

        fileService.update(savedPhoto,ResourceType.HOTEL, hotel.getId());
        return hotelRepository.save(hotel);
    }

    public Hotel updateWithDetails(@NotNull HotelDetails hotelDetails) {
        Hotel hotel = hotelRepository.findById(hotelDetails.getHotel().getId()).orElseThrow(() -> new RequestNotValidException("Hotel not found"));
        hotel.setHotelDetails(hotelDetails);
        return hotelRepository.save(hotel);
    }


    public void delete(@NotNull Hotel hotel) {
        Hotel hotel1=hotelRepository.findById(hotel.getId()).orElseThrow(() -> new RequestNotValidException("Hotel not found"));
        hotelRepository.delete(hotel);
    }







}
