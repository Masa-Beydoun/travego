package SpringBootStarterProject.City_Place_Package.Service;

import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.City_Place_Package.Repository.CountryRepository;
import SpringBootStarterProject.City_Place_Package.Request.CountryRequest;
import SpringBootStarterProject.City_Place_Package.Response.CountryResponse;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final ObjectsValidator<CountryRequest> objectsValidator;


    public ApiResponseClass findAll() {
        List<Country> countries = countryRepository.findAll();
        List<CountryResponse> response = new ArrayList<>();
        for (Country country : countries) {
            response.add(CountryResponse.builder()
                    .id(country.getId())
                    .iso(country.getIso())
                    .dial(country.getDial())
                    .iso3(country.getIso3())
                    .currency_name(country.getCurrency_name())
                    .currency(country.getCurrency())
                    .name(country.getName())
                    .build()
            );
        }
        return new ApiResponseClass("Get all country done successfully", HttpStatus.ACCEPTED, LocalDateTime.now(),response);
    }

    public ApiResponseClass findById(Integer id) {
        Country country = countryRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Country not found")
        );
        CountryResponse response = CountryResponse.builder()
                .id(country.getId())
                .iso(country.getIso())
                .dial(country.getDial())
                .iso3(country.getIso3())
                .currency_name(country.getCurrency_name())
                .currency(country.getCurrency())
                .name(country.getName())
                .build();
        return new ApiResponseClass("Get country done successfully", HttpStatus.ACCEPTED, LocalDateTime.now(),response);
    }
    public ApiResponseClass addNewCountry(CountryRequest request) {
        objectsValidator.validate(request);
        Country country = Country.builder()
                .iso(request.getIso())
                .iso3(request.getIso3())
                .dial(request.getDial())
                .currency_name(request.getCurrency_name())
                .currency(request.getCurrency())
                .name(request.getName())
                .build();

        countryRepository.save(country);
        CountryResponse response = CountryResponse.builder()
                .id(country.getId())
                .iso(country.getIso())
                .dial(country.getDial())
                .iso3(country.getIso3())
                .currency_name(country.getCurrency_name())
                .currency(country.getCurrency())
                .name(country.getName())
                .build();

        return new ApiResponseClass("Create country done successfully", HttpStatus.CREATED, LocalDateTime.now(),response);
    }

    public ApiResponseClass updateCountry(Integer id, CountryRequest request) {
        objectsValidator.validate(request);
        Country country = countryRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Country not found")
        );
        country.setIso(request.getIso());
        country.setDial(request.getDial());
        country.setIso3(request.getIso3());
        country.setCurrency_name(request.getCurrency_name());
        country.setCurrency(request.getCurrency());
        country.setName(request.getName());
        countryRepository.save(country);

        CountryResponse response = CountryResponse.builder()
                .id(country.getId())
                .iso(country.getIso())
                .dial(country.getDial())
                .iso3(country.getIso3())
                .currency_name(country.getCurrency_name())
                .currency(country.getCurrency())
                .name(country.getName())
                .build();
        return new ApiResponseClass("Update country done successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }

    public ApiResponseClass deleteCountry(Integer id) {
        Country country = countryRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Country not found")
        );
        countryRepository.delete(country);
        return new ApiResponseClass("Delete country done successfully", HttpStatus.OK, LocalDateTime.now());
    }
}
