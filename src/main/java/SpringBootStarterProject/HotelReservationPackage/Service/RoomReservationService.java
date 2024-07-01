package SpringBootStarterProject.HotelReservationPackage.Service;

import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservation;
import SpringBootStarterProject.HotelReservationPackage.Model.RoomReservation;
import SpringBootStarterProject.HotelReservationPackage.Repository.RoomReservationRepository;
import SpringBootStarterProject.HotelReservationPackage.Request.RoomReservationRequest;
import SpringBootStarterProject.HotelReservationPackage.Response.RoomReservationResponse;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.Room;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.RoomRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomReservationService {
    private final RoomReservationRepository roomReservationRepository;
    private final RoomRepository roomRepository;
    private final HotelDetailsRepository hotelDetailsRepository;
    private final ObjectsValidator<RoomReservationRequest> validator;

    public RoomReservationResponse save(RoomReservationRequest request) {
        validator.validate(request);

        Room room = roomRepository.findById(request.getRoomId()).orElseThrow(()-> new RequestNotValidException("Room not found"));

        HotelDetails details = hotelDetailsRepository.findById(room.getHotelDetails().getId()).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
        Double price = room.getPrice()* request.getNumberOfRooms()+ request.getNumberOfExtraBed()*details.getPriceForExtraBed();


        RoomReservation reservation= RoomReservation.builder()
                .notes(request.getNotes())
                .room(room)
                .priceForOneRoom(room.getPrice())
                .numberOfRooms(request.getNumberOfRooms())
                .totalExtraBed(request.getNumberOfExtraBed())
                .totalPrice(price)
                .build();
        roomReservationRepository.save(reservation);
        return RoomReservationResponse.builder()
                .id(reservation.getId())
                .notes(reservation.getNotes())
                .roomId(reservation.getRoom().getId())
                .numberOfRooms(reservation.getNumberOfRooms())
                .totalExtraBed(reservation.getTotalExtraBed())
                .priceForOneRoom(reservation.getPriceForOneRoom())
                .totalPrice(reservation.getTotalPrice())
                .build();

    }





}
