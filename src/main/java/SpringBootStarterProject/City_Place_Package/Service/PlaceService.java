package SpringBootStarterProject.City_Place_Package.Service;

import SpringBootStarterProject.City_Place_Package.DTO.TimeDTO;
import SpringBootStarterProject.City_Place_Package.Models.Location;
import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.City_Place_Package.Repository.CityRepository;
import SpringBootStarterProject.City_Place_Package.Repository.CountryRepository;
import SpringBootStarterProject.City_Place_Package.Repository.LocationRepository;
import SpringBootStarterProject.City_Place_Package.Repository.PlaceRepository;
import SpringBootStarterProject.City_Place_Package.Request.PlaceRequest;
import SpringBootStarterProject.City_Place_Package.Response.PlaceResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {


    private final PlaceRepository placeRepository;
    private final ObjectsValidator<PlaceRequest> placeRequestObjectsValidator;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final LocationRepository locationRepository;


    public List<PlaceResponse> getAllPlaces() {
       List<Place> places = placeRepository.findAll();
       List<PlaceResponse> placeResponses = new ArrayList<>();
       for (Place place : places) {
           placeResponses.add(PlaceResponse.builder()
                   .id(place.getId())
                   .name(place.getName())
                   .description(place.getDescription())
                   .country(place.getCountry().getName())
                   .city(place.getCity().getName())
                   .openingTime(place.getOpeningTime())
                   .closingTime(place.getClosingTime())
                   .location(place.getLocation())
                   .build()
           );
       }
       return placeResponses;
    }


    public LocalTime convertToLocalTime(TimeDTO timeDTO) {
        return LocalTime.of(timeDTO.getHour(),
                timeDTO.getMinute(),
                timeDTO.getSecond(),
                timeDTO.getMillisecond());
    }

    public PlaceResponse getPlaceById(int id) {
        Place place = placeRepository.findById(id).orElseThrow(
                () -> new RequestNotValidException("Place not found")
        );

        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .description(place.getDescription())
                .country(place.getCountry().getName())
                .city(place.getCity().getName())
                .openingTime(place.getOpeningTime())
                .closingTime(place.getClosingTime())
                .location(place.getLocation())
                .build();
    }
    public List<PlaceResponse> getPlacesByCity(Integer city_id) {
        List<Place> places = placeRepository.findByCityId(city_id);
        List<PlaceResponse> placeResponses = new ArrayList<>();
        for (Place place : places) {
            placeResponses.add(PlaceResponse.builder()
                    .id(place.getId())
                    .name(place.getName())
                    .description(place.getDescription())
                    .country(place.getCountry().getName())
                    .city(place.getCity().getName())
                    .openingTime(place.getOpeningTime())
                    .closingTime(place.getClosingTime())
                    .location(place.getLocation())
                    .build()
                    );
        }
        return placeResponses;
    }

    @Transactional
    public PlaceResponse createPlace(PlaceRequest request) {
        placeRequestObjectsValidator.validate(request);


        Location location = Location.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
        locationRepository.save(location);


        TimeDTO openingTimeDTO = request.getOpeningTime();
        LocalTime openingTime = convertToLocalTime(openingTimeDTO);
        TimeDTO closingTimeDTO = request.getClosingTime();
        LocalTime closingTime = convertToLocalTime(closingTimeDTO);
        Place place = Place.builder()
                .name(request.getName())
                .description(request.getDescription())
                .country(countryRepository.findByName(request.getCountry()).orElseThrow(
                        ()-> new RequestNotValidException("country not found")
                ))
                .city(cityRepository.findByName(request.getCity()).orElseThrow(
                        ()-> new RequestNotValidException("city not found")
                ))
                .openingTime(openingTime)
                .closingTime(closingTime)
                .location(location)
                .build();

        placeRepository.save(place);

        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .description(place.getDescription())
                .country(place.getCountry().getName())
                .city(place.getCity().getName())
                .openingTime(place.getOpeningTime())
                .closingTime(place.getClosingTime())
                .location(place.getLocation())
                .build();
    }

    @Transactional
    public PlaceResponse updatePlace(Integer id, PlaceRequest request) {
        Place place = placeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Place not found")
        );

        Location location = locationRepository.findById(place.getLocation().getId()).orElseThrow(
                ()->new RequestNotValidException("location not found")
        );

        TimeDTO openingTimeDTO = request.getOpeningTime();
        LocalTime openingTime = convertToLocalTime(openingTimeDTO);
        TimeDTO closingTimeDTO = request.getClosingTime();
        LocalTime closingTime = convertToLocalTime(closingTimeDTO);

        place.setName(request.getName());
        place.setDescription(request.getDescription());
        place.setCountry(countryRepository.findByName(request.getCountry()).orElseThrow(
                ()-> new RequestNotValidException("country not found")
        ));
        place.setCity(cityRepository.findByName(request.getCity()).orElseThrow(
                ()-> new RequestNotValidException("city not found")
        ));

        place.setOpeningTime(openingTime);
        place.setClosingTime(closingTime);
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        locationRepository.save(location);
        place.setLocation(location);
        placeRepository.save(place);

        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .description(place.getDescription())
                .country(place.getCountry().getName())
                .city(place.getCity().getName())
                .openingTime(place.getOpeningTime())
                .closingTime(place.getClosingTime())
                .location(place.getLocation())
                .build();
    }

    public String deletePlace(Integer id) {
        Place place = placeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Place not found")
        );
        placeRepository.delete(place);
        return "Place deleted successfully";
    }
}
