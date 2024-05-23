package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.RoomServices;
import SpringBootStarterProject.HotelsPackage.Repository.RoomServicesRepository;
import SpringBootStarterProject.HotelsPackage.Request.RoomServicesRequest;
import SpringBootStarterProject.HotelsPackage.Response.RoomServicesResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.Trip_package.Models.HotelServices;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class RoomServicesService {
    private final RoomServicesRepository roomServicesRepository;
    private final ObjectsValidator<RoomServicesRequest> validator;


    public List<RoomServicesResponse> getAllRoomServices() {
        List<RoomServices> all =  roomServicesRepository.findAll();
        List<RoomServicesResponse> roomServicesResponses = new ArrayList<>();
        for (RoomServices roomServices : all) {
            roomServicesResponses.add(RoomServicesResponse.builder()
                    .id(roomServices.getId())
                    .name(roomServices.getName())
                    .build());
        }
        return roomServicesResponses;
    }

    public RoomServicesResponse getRoomServiceById(Integer id) {
        RoomServices roomServices = roomServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Room Service not found")
        );
        return RoomServicesResponse.builder()
                .id(roomServices.getId())
                .name(roomServices.getName())
                .build();
    }

    public RoomServicesResponse createRoomService(RoomServicesRequest request) {
        validator.validate(request);

        RoomServices roomServices = RoomServices.builder()
                .name(request.getTripName())
                .build();
        roomServicesRepository.save(roomServices);

        return RoomServicesResponse.builder()
                .id(roomServices.getId())
                .name(roomServices.getName())
                .build();
    }

    public RoomServicesResponse updateRoomService(RoomServicesRequest request , Integer id) {
        validator.validate(request);

        RoomServices roomServices = roomServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Room Service Not Found")
        );
        roomServices.setName(request.getTripName());
        roomServicesRepository.save(roomServices);

        return RoomServicesResponse.builder()
                .id(roomServices.getId())
                .name(roomServices.getName())
                .build();
    }


    public String deleteRoomService(Integer id) {
        RoomServices roomServices = roomServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Room Service Not Found")
        );
        roomServicesRepository.delete(roomServices);
        return "Room Service deleted successfully";
    }

}
