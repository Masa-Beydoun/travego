package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.Room;
import SpringBootStarterProject.HotelsPackage.Models.RoomServices;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.RoomRepository;
import SpringBootStarterProject.HotelsPackage.Repository.RoomServicesRepository;
import SpringBootStarterProject.HotelsPackage.Request.RoomRequest;
import SpringBootStarterProject.HotelsPackage.Response.RoomResponse;
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
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelDetailsRepository hotelDetailsRepository;
    private final RoomServicesRepository roomServicesRepository;
    private final ObjectsValidator<RoomRequest> validator;

    public ApiResponseClass getAllRoomsByHotelId(Integer hotelDetailsId) {
        List<Room> rooms = roomRepository.findAllByHotelDetailsId(hotelDetailsId);
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room roomResponse : rooms) {

           RoomResponse response=  RoomResponse.builder()
                .id(roomResponse.getId())
                .roomServices(roomResponse.getRoomServices())
                .hotelDetails(roomResponse.getHotelDetails())
                .price(roomResponse.getPrice())
                .space(roomResponse.getSpace())
                .maxNumOfPeople(roomResponse.getMaxNumOfPeople())
                .num_of_bed(roomResponse.getNum_of_bed())
                .build();
           roomResponses.add(response);
        }
        return new ApiResponseClass("Rooms got successfully", HttpStatus.OK, LocalDateTime.now(),roomResponses);

    }


    public ApiResponseClass save(RoomRequest request) {
        validator.validate(request);

        HotelDetails hotelDetails = hotelDetailsRepository.findById(request.getHotelDetailsId()).orElseThrow(()-> new RequestNotValidException("Hotel Details Not Found"));

        List<String> requestServices= request.getRoomServices();
        List<RoomServices> services=new ArrayList<>();

        for (String requestService : requestServices) {

            services.add(roomServicesRepository.findByName(requestService));
        }


        Room roomResponse = Room.builder()
                .hotelDetails(hotelDetails)
                .price(request.getPrice())
                .space(request.getSpace())
                .maxNumOfPeople(request.getMaxNumOfPeople())
                .num_of_bed(request.getNum_of_bed())
                .roomServices(services)
                .build();


        RoomResponse response = RoomResponse.builder()
                .id(roomResponse.getId())
                .roomServices(roomResponse.getRoomServices())
                .hotelDetails(roomResponse.getHotelDetails())
                .price(roomResponse.getPrice())
                .space(roomResponse.getSpace())
                .maxNumOfPeople(roomResponse.getMaxNumOfPeople())
                .num_of_bed(roomResponse.getNum_of_bed())
                .build();
        return new ApiResponseClass("Room Created successfully", HttpStatus.OK, LocalDateTime.now(),response);

    }
}
