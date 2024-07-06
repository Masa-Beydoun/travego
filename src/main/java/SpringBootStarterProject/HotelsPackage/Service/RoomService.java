package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Enum.RoomType;
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
        List<Room> rooms = roomRepository.findAllByHotelDetailsId(hotelDetailsId).orElseThrow(()->new RequestNotValidException("room not found"));
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room roomResponse : rooms) {

           RoomResponse response=  RoomResponse.builder()
                .id(roomResponse.getId())
                .roomServices(roomResponse.getRoomServices())
                .price(roomResponse.getPrice())
                .space(roomResponse.getSpace())
                .maxNumOfPeople(roomResponse.getMaxNumOfPeople())
                .num_of_bed(roomResponse.getNum_of_bed())
                .totalNumberOfRooms(roomResponse.getTotalNumberOfRooms())
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

        RoomType roomType = RoomType.valueOf(request.getRoomType().name());
        Room room = Room.builder()
                .hotelDetails(hotelDetails)
                .price(request.getPrice())
                .space(request.getSpace())
                .maxNumOfPeople(request.getMaxNumOfPeople())
                .num_of_bed(request.getNum_of_bed())
                .roomServices(services)
                .type(roomType)
                .totalNumberOfRooms(request.getTotalNumberOfRooms())
                .build();

        roomRepository.save(room);

        RoomResponse response = RoomResponse.builder()
                .id(room.getId())
                .roomServices(room.getRoomServices())
                .price(room.getPrice())
                .space(room.getSpace())
                .maxNumOfPeople(room.getMaxNumOfPeople())
                .num_of_bed(room.getNum_of_bed())
                .type(room.getType().name())
                .totalNumberOfRooms(room.getTotalNumberOfRooms())
                .build();
        return new ApiResponseClass("Room Created successfully", HttpStatus.OK, LocalDateTime.now(),response);

    }

    public ApiResponseClass delete(Integer id) {
        hotelDetailsRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel Details Not Found"));
        hotelDetailsRepository.deleteById(id);
        return new ApiResponseClass("Hotel Deleted Successfully", HttpStatus.OK, LocalDateTime.now());
    }

    public ApiResponseClass update(Integer id, RoomRequest request) {
        validator.validate(request);

        Room room =roomRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Rooms Not Found"));
        hotelDetailsRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel Details Not Found"));


        List<String> requestServices= request.getRoomServices();
        List<RoomServices> services=new ArrayList<>();
        for (String requestService : requestServices) {
            services.add(roomServicesRepository.findByName(requestService));
        }

        room.setRoomServices(services);
        room.setPrice(request.getPrice());
        room.setSpace(request.getSpace());
        room.setMaxNumOfPeople(request.getMaxNumOfPeople());
        room.setNum_of_bed(request.getNum_of_bed());

        roomRepository.save(room);

        RoomResponse roomResponse = RoomResponse.builder()
                .id(room.getId())
                .roomServices(room.getRoomServices())
                .num_of_bed(room.getNum_of_bed())
                .price(room.getPrice())
                .space(room.getSpace())
                .maxNumOfPeople(room.getMaxNumOfPeople())
                .totalNumberOfRooms(room.getTotalNumberOfRooms())
                .build();
        return new ApiResponseClass("Room Updated successfully", HttpStatus.OK, LocalDateTime.now(),roomResponse);



    }

    public ApiResponseClass getById(Integer id) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Rooms Not Found"));
        RoomResponse roomResponse = RoomResponse.builder()
                .id(room.getId())
                .roomServices(room.getRoomServices())
                .num_of_bed(room.getNum_of_bed())
                .price(room.getPrice())
                .space(room.getSpace())
                .maxNumOfPeople(room.getMaxNumOfPeople())
                .totalNumberOfRooms(room.getTotalNumberOfRooms())
                .build();
        return new ApiResponseClass("Room Found", HttpStatus.OK, LocalDateTime.now(),roomResponse);
    }
}
