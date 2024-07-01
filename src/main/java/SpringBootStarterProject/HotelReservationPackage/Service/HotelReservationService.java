package SpringBootStarterProject.HotelReservationPackage.Service;

import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservation;
import SpringBootStarterProject.HotelReservationPackage.Model.RoomReservation;
import SpringBootStarterProject.HotelReservationPackage.Repository.HotelReservationRepository;
import SpringBootStarterProject.HotelReservationPackage.Repository.RoomReservationRepository;
import SpringBootStarterProject.HotelReservationPackage.Request.HotelReservationRequest;
import SpringBootStarterProject.HotelReservationPackage.Request.RoomReservationRequest;
import SpringBootStarterProject.HotelReservationPackage.Response.HotelReservationResponse;
import SpringBootStarterProject.HotelReservationPackage.Response.RoomReservationResponse;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.Room;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Repository.RoomRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ReservationConfirmPackage.Model.ConfirmReservation;
import SpringBootStarterProject.ReservationConfirmPackage.Repository.ConfirmReservationRepository;
import SpringBootStarterProject.ReservationConfirmPackage.Response.ConfirmReservationResponse;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static SpringBootStarterProject.HotelReservationPackage.Enum.HotelReservationStatus.*;

@Service
@RequiredArgsConstructor
public class HotelReservationService {

    private final HotelReservationRepository hotelReservationRepository;
    private final ObjectsValidator<HotelReservationRequest> validator;
    private final HotelRepository hotelRepository;
    private final HotelDetailsRepository hotelDetailsRepository;
    private final ClientRepository clientRepository;
    private final ConfirmReservationRepository confirmReservationRepository;
    private final RoomReservationService roomReservationService;
    private final RoomRepository roomRepository;
    private final RoomReservationRepository roomReservationRepository;


    public ApiResponseClass createReservation(HotelReservationRequest request){
        validator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
        HotelDetails details = hotelDetailsRepository.findByHotelId(hotel.getId()).orElseThrow(()-> new RequestNotValidException("Hotel Details not found"));


        List<RoomReservationResponse> roomReservations1=new ArrayList<>();
        List<RoomReservationRequest> roomRequest=request.getRoomReservations();
        Double totalPrice=0.0;
        for(RoomReservationRequest roomReservationRequest:roomRequest){
            Room room = roomRepository.findById(roomReservationRequest.getRoomId()).orElseThrow(()->new RequestNotValidException("Room not found"));
            if(!Objects.equals(room.getHotelDetails().getId(), details.getId())){
                throw new RequestNotValidException("Hotel Details Id Mismatch With Room Id");
            }
        }


        for(RoomReservationRequest roomReservationRequest:roomRequest){
            RoomReservationResponse r1= roomReservationService.save(roomReservationRequest);
            totalPrice+=r1.getTotalPrice();
            roomReservations1.add(r1);
        }

        totalPrice*=totalDay(request.getStartDate(),request.getEndDate());
        HotelReservation reservation = HotelReservation.builder()
                .hotel(hotel)
                .client(client)
                .endDate(request.getEndDate())
                .startDate(request.getStartDate())
                .status(CREATED.name())
                .totalPrice(totalPrice)
                .build();
        hotelReservationRepository.save(reservation);


        List<RoomReservationResponse> roomReservationResponse2=new ArrayList<>();
        for(RoomReservationResponse roomReservationResponse:roomReservations1){
            RoomReservation  r1 = roomReservationRepository.findById(roomReservationResponse.getId()).orElseThrow(()-> new RequestNotValidException("Room Reservation Not Found"));
            r1.setHotelReservationId(reservation.getId());
            roomReservationRepository.save(r1);
            RoomReservationResponse response = RoomReservationResponse.builder()
                    .id(r1.getId())
                    .hotelReservationId(r1.getHotelReservationId())
                    .priceForOneRoom(r1.getPriceForOneRoom())
                    .totalPrice(r1.getTotalPrice())
                    .notes(r1.getNotes())
                    .totalExtraBed(r1.getTotalExtraBed())
                    .numberOfRooms(r1.getNumberOfRooms())
                    .roomId(r1.getRoom().getId())
                    .build();
            roomReservationResponse2.add(response);
        }
        HotelReservationResponse response = HotelReservationResponse.builder()
                .id(reservation.getId())
                .hotelName(reservation.getHotel().getName())
                .hotelId(reservation.getHotel().getId())
                .endDate(reservation.getEndDate())
                .startDate(reservation.getStartDate())
                .status(reservation.getStatus())
                .roomReservationResponses(roomReservationResponse2)
                .clientId(reservation.getClient().getId())
                .totalPrice(reservation.getTotalPrice())
                .build();
        return new ApiResponseClass("Created successfully, waiting for accepting",HttpStatus.CREATED,LocalDateTime.now(),response);
    }



    public ApiResponseClass startingReservation(Integer reservationId){
        HotelReservation reservation = hotelReservationRepository.findById(reservationId).orElseThrow(()->new RequestNotValidException("Reservation Not Found"));
        if(reservation.getStatus().equals(FINISHED.name())){
            return new ApiResponseClass("Reservation finished",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        if(reservation.getStatus().equals(CANCELLED.name())){
            return new ApiResponseClass("Reservation already cancelled",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        ConfirmReservation confirmReservation = confirmReservationRepository.findByReservationId(reservationId).orElseThrow(()->new RequestNotValidException("Reservation is rejected by "));
        LocalDate today = LocalDate.now();
        if(reservation.getStartDate().isAfter(LocalDate.now()) && !reservation.getStatus().equals(DURING.name())){
            reservation.setStatus(DURING.name());
            return new ApiResponseClass("Reservation Started now", HttpStatus.OK,LocalDateTime.now());
        }

        return new ApiResponseClass("nothing to update",HttpStatus.OK, LocalDateTime.now());

    }


    public ApiResponseClass acceptHotelReservation(Integer reservationId) {
        HotelReservation reservation = hotelReservationRepository.findById(reservationId).orElseThrow(()->new RequestNotValidException("Reservation Not Found"));
        if(reservation.getStatus().equals(FINISHED.name())){
            return new ApiResponseClass("Reservation finished",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        if(reservation.getStatus().equals(CANCELLED.name())){
            return new ApiResponseClass("Reservation cancelled",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        reservation.setStatus(ACCEPTED.name());
        hotelReservationRepository .save(reservation);
        return new ApiResponseClass("Hotel reservation cancelled",HttpStatus.OK, LocalDateTime.now());

    }




    public ApiResponseClass cancelReservation(Integer reservationId){

        HotelReservation reservation = hotelReservationRepository.findById(reservationId).orElseThrow(()->new RequestNotValidException("Reservation Not Found"));
        if(reservation.getStatus().equals(FINISHED.name())){
            return new ApiResponseClass("Reservation finished",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        if(reservation.getStatus().equals(CANCELLED.name())){
            return new ApiResponseClass("Reservation already cancelled",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        LocalDate today = LocalDate.now();
//        if(reservation.getStartDate().isAfter(new LocalDate(today.getYear(),today.getMonthValue(),today.getDayOfMonth()+ 2)){
//            return new ApiResponseClass("Reservation can't be Cancelled", HttpStatus.OK,LocalDate.now());
//        }
        reservation.setStatus(CANCELLED.name());
        hotelReservationRepository.save(reservation);
        return new ApiResponseClass("Hotel reservation cancelled",HttpStatus.OK, LocalDateTime.now());

    }






    public ApiResponseClass deleteReservation(Integer id) {
        HotelReservation reservation = hotelReservationRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Reservation Not found"));
        if(!(reservation.getStatus().equals(CANCELLED.name()) || reservation.getStatus().equals(FINISHED.name()))){
            return new ApiResponseClass("You can't delete a reservation that is not either cancelled or finished",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        hotelReservationRepository.delete(reservation);
        return new ApiResponseClass("Hotel reservation deleted",HttpStatus.OK, LocalDateTime.now());
    }



    public Integer totalDay(LocalDate beginDate, LocalDate endDate){

        Integer yearDif = endDate.getYear() - beginDate.getYear();
        Integer monthDif = endDate.getMonthValue() - beginDate.getMonthValue();
        Integer dayDif = endDate.getDayOfMonth() - beginDate.getDayOfMonth();

        return dayDif + 30*monthDif + 365*yearDif;

    }

}
