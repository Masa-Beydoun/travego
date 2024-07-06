package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.RoomServices;
import SpringBootStarterProject.HotelsPackage.Repository.RoomServicesRepository;
import SpringBootStarterProject.HotelsPackage.Request.RoomServicesRequest;
import SpringBootStarterProject.HotelsPackage.Response.RoomServicesResponse;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class RoomServicesService {
    private final RoomServicesRepository roomServicesRepository;
    private final ObjectsValidator<RoomServicesRequest> validator;


    public ApiResponseClass getAllRoomServices() {
        List<RoomServices> all =  roomServicesRepository.findAll();
        List<RoomServicesResponse> roomServicesResponses = new ArrayList<>();
        for (RoomServices roomServices : all) {
            roomServicesResponses.add(RoomServicesResponse.builder()
                    .id(roomServices.getId())
                    .name(roomServices.getName())
                    .build());
        }
        return new ApiResponseClass("Room-Services get successfully", HttpStatus.OK, LocalDateTime.now(),roomServicesResponses);

    }

    public ApiResponseClass getRoomServiceById(Integer id) {
        RoomServices roomServices = roomServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Room Service not found")
        );
        RoomServicesResponse response = RoomServicesResponse.builder()
                .id(roomServices.getId())
                .name(roomServices.getName())
                .build();
        return new ApiResponseClass("Room-Service got successfully", HttpStatus.OK, LocalDateTime.now(),response);

    }

    public ApiResponseClass createRoomService(RoomServicesRequest request) {
        validator.validate(request);

        RoomServices roomServices = RoomServices.builder()
                .name(request.getRoomServiceName())
                .build();
        roomServicesRepository.save(roomServices);

        RoomServicesResponse response = RoomServicesResponse.builder()
                .id(roomServices.getId())
                .name(roomServices.getName())
                .build();
        return new ApiResponseClass("Room-Service created successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }

    public ApiResponseClass updateRoomService(RoomServicesRequest request , Integer id) {
        validator.validate(request);

        RoomServices roomServices = roomServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Room Service Not Found")
        );
        roomServices.setName(request.getRoomServiceName());
        roomServicesRepository.save(roomServices);

        RoomServicesResponse response = RoomServicesResponse.builder()
                .id(roomServices.getId())
                .name(roomServices.getName())
                .build();
        return new ApiResponseClass("Room-Services updated successfully", HttpStatus.OK, LocalDateTime.now(),response);

    }


    public ApiResponseClass deleteRoomService(Integer id) {
        RoomServices roomServices = roomServicesRepository.findById(id).orElseThrow(
                ()-> new RequestNotValidException("Room Service Not Found")
        );
        roomServicesRepository.delete(roomServices);
        return new ApiResponseClass("Room Service deleted successfully",HttpStatus.OK,LocalDateTime.now());
    }

}
