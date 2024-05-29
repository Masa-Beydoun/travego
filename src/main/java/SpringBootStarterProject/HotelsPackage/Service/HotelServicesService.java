package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.HotelServices;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelServicesRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelServicesRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.HotelsPackage.Response.HotelServicesResponse;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@Builder
public class HotelServicesService {

    private final HotelServicesRepository hotelServicesRepository;
    private final ObjectsValidator<HotelServicesRequest> validator;
    private final HotelDetailsRepository hotelDetailsRepository;


    public ApiResponseClass getAllHotelServices() {
        List<HotelServices> hotelServices =  hotelServicesRepository.findAll();
        List<HotelServicesResponse> hotelServicesResponses = new ArrayList<>();
        for (HotelServices hotelService : hotelServices) {
            hotelServicesResponses.add(HotelServicesResponse.builder()
                    .id(hotelService.getId())
                    .name(hotelService.getName())
                    .build());
        }
        return new ApiResponseClass("All Hotel-Services get successfully", HttpStatus.OK, LocalDateTime.now(),hotelServicesResponses);

    }

    public ApiResponseClass getHotelServiceById(Integer id) {
        HotelServices hotelServices = hotelServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Hotel Service not found")
        );
        HotelServicesResponse response =  HotelServicesResponse.builder()
                .id(hotelServices.getId())
                .name(hotelServices.getName())
                .build();
        return new ApiResponseClass("Hotel-Service get successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }

    public ApiResponseClass createHotelService(HotelServicesRequest request) {
        validator.validate(request);

        HotelServices h = hotelServicesRepository.findByName(request.getHotelServiceName()).orElse(null);
        if(h != null) throw new RequestNotValidException("Hotel Service already exists");

        HotelServices hotelService = HotelServices.builder()
                .name(request.getHotelServiceName())
                .build();
        hotelServicesRepository.save(hotelService);

        HotelServicesResponse response = HotelServicesResponse.builder()
                .id(hotelService.getId())
                .name(hotelService.getName())
                .build();
        return new ApiResponseClass("Hotel-Service Cretaed successfully", HttpStatus.CREATED, LocalDateTime.now(),response);

    }

    public ApiResponseClass updateHotelService(HotelServicesRequest request , Integer id) {
        validator.validate(request);

        HotelServices hotelServices = hotelServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Hotel Service Not Found")
        );
        hotelServices.setName(request.getHotelServiceName());
        hotelServicesRepository.save(hotelServices);

        HotelServicesResponse response = HotelServicesResponse.builder()
                .id(hotelServices.getId())
                .name(hotelServices.getName())
                .build();
        return new ApiResponseClass("Hotel-Service Updated successfully", HttpStatus.OK, LocalDateTime.now(),response);

    }


    public ApiResponseClass deleteHotelService(Integer id) {
        HotelServices hotelServices = hotelServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Hotel Service Not Found")
        );
        hotelServicesRepository.delete(hotelServices);
        return new ApiResponseClass("Hotel Service deleted successfully", HttpStatus.OK, LocalDateTime.now());
    }

}
