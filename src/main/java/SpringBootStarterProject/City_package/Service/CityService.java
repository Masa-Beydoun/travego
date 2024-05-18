package SpringBootStarterProject.City_package.Service;

import SpringBootStarterProject.City_package.Models.City;
import SpringBootStarterProject.City_package.Repository.CityRepository;
import SpringBootStarterProject.City_package.Request.CreateCityRequest;
import SpringBootStarterProject.City_package.Request.GetCityByIdRequest;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {

    private ObjectsValidator<GetCityByIdRequest>getCityByIdValidator;
    private ObjectsValidator<CreateCityRequest>createCityValidator;

    private final CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll();
    }
    public City findById(GetCityByIdRequest id) {
        getCityByIdValidator.validate(id);
        return cityRepository.findById(id.getId()).orElseThrow(()-> new RequestNotValidException("Id not found"));
    }
//    public City createCity(CreateCityRequest request) {
//        createCityValidator.validate(request);
//
//    }



}
