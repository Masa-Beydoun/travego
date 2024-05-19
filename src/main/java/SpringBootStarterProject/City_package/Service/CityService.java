package SpringBootStarterProject.City_package.Service;

import SpringBootStarterProject.City_package.Models.Country;
import SpringBootStarterProject.City_package.Response.CityResponse;
import SpringBootStarterProject.City_package.Models.City;
import SpringBootStarterProject.City_package.Repository.CityRepository;
import SpringBootStarterProject.City_package.Repository.CountryRepository;
import SpringBootStarterProject.City_package.Request.CreateCityRequest;
import SpringBootStarterProject.City_package.Request.GetCityByIdRequest;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {


    private CountryRepository countryRepository;
//    @Autowired
//    private ObjectsValidator<GetCityByIdRequest>getCityByIdValidator;
    @Autowired
    private ObjectsValidator<CreateCityRequest>createCityValidator;

    private final CityRepository cityRepository;

    public List<CityResponse> GetALlCities() {
        List<City> cityList = cityRepository.findAll();
        List<CityResponse> cityResponseList = new ArrayList<>();
        for (City city : cityList) {
            cityResponseList.add(CityResponse.builder()
                    .id(city.getId())
                    .cityName(city.getName())
                    .countryName(city.getCountry().getName())
                    .build());
        }
        return cityResponseList;
        }
    public CityResponse findById(Integer id) {
//        getCityByIdValidator.validate(id);
         City city = cityRepository.findById(id).orElseThrow(
                 ()-> new RequestNotValidException("Id not found"));
         return CityResponse.builder()
                 .id(city.getId())
                 .cityName(city.getName())
                 .countryName(city.getCountry().getName())
                 .build();
    }

    @Transactional
    public CityResponse createCity(CreateCityRequest request) {
        createCityValidator.validate(request);

        City city = City.builder()
                .name(request.getName())
                .country(countryRepository.findByName(
                        request.getCountry()).orElseThrow(
                                ()->new RequestNotValidException("country not exist")))
                .build();
        cityRepository.save(city);
        return  CityResponse.builder()
                .id(city.getId())
                .cityName(city.getName())
                .countryName(city.getCountry().getName())
                .build();

    }

    @Transactional
    public CityResponse EditCity(CreateCityRequest request , Integer id) {

        createCityValidator.validate(request);
//        getCityByIdValidator.validate(id);
//        if()


        City city = cityRepository.findById(id).orElseThrow(
                ()->new RequestNotValidException("Id not found"));
        city.setName(request.getName());
        city.setCountry(countryRepository.findByName(
                request.getCountry()).orElseThrow(
                ()->new RequestNotValidException("country not exist")));
        cityRepository.save(city);
        return CityResponse.builder()
                .id(city.getId())
                .cityName(city.getName())
                .countryName(city.getCountry().getName())
                .build();
    }

    public String DeleteCity(Integer id) {
//        getCityByIdValidator.validate(id);
        cityRepository.delete(cityRepository.findById(
                id).orElseThrow(
                        () -> new RequestNotValidException("Id not found")));
        return "Delete Done successfully";
    }

    public List<CityResponse> getCitiesByCountry(String countryId) {
        Country country = countryRepository.findByName(countryId).orElseThrow(
                ()-> new RequestNotValidException("Country not found"));
        List<City> cityList = cityRepository.findByCountry(country.getName());
        List<CityResponse> cityResponseList = new ArrayList<>();
        for (City city : cityList) {
            cityResponseList.add(CityResponse.builder()
                    .id(city.getId())
                    .cityName(city.getName())
                    .countryName(city.getCountry().getName())
                    .build());
        }
        return cityResponseList;
    }



}
