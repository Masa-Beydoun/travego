package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.HotelServices;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelServicesRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelServicesRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelServicesResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@Builder
public class HotelServicesService {

    private final HotelServicesRepository hotelServicesRepository;
    private final ObjectsValidator<HotelServicesRequest> validator;
    private final HotelDetailsRepository hotelDetailsRepository;


    public List<HotelServicesResponse> getAllHotelServices() {
        List<HotelServices> hotelServices =  hotelServicesRepository.findAll();
        List<HotelServicesResponse> hotelServicesResponses = new ArrayList<>();
        for (HotelServices hotelService : hotelServices) {
            hotelServicesResponses.add(HotelServicesResponse.builder()
                    .id(hotelService.getId())
                    .name(hotelService.getName())
                    .build());
        }
        return hotelServicesResponses;
    }

    public HotelServicesResponse getHotelServiceById(Integer id) {
        HotelServices hotelServices = hotelServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Hotel Service not found")
        );
        return HotelServicesResponse.builder()
                .id(hotelServices.getId())
                .name(hotelServices.getName())
                .build();
    }

    public HotelServicesResponse createHotelService(HotelServicesRequest request) {
        validator.validate(request);

        HotelServices h = hotelServicesRepository.findByName(request.getHotelServiceName()).orElse(null);
        if(h != null) throw new RequestNotValidException("Hotel Service already exists");

        HotelServices hotelService = HotelServices.builder()
                .name(request.getHotelServiceName())
                .build();
        hotelServicesRepository.save(hotelService);

        return HotelServicesResponse.builder()
                .id(hotelService.getId())
                .name(hotelService.getName())
                .build();
    }

    public HotelServicesResponse updateHotelService(HotelServicesRequest request , Integer id) {
        validator.validate(request);

        HotelServices hotelServices = hotelServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Hotel Service Not Found")
        );
        hotelServices.setName(request.getHotelServiceName());
        hotelServicesRepository.save(hotelServices);

        return HotelServicesResponse.builder()
                .id(hotelServices.getId())
                .name(hotelServices.getName())
                .build();
    }


    public String deleteHotelService(Integer id) {
        HotelServices hotelServices = hotelServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Hotel Service Not Found")
        );
        hotelServicesRepository.delete(hotelServices);
        return "Hotel Service deleted successfully";
    }

}
