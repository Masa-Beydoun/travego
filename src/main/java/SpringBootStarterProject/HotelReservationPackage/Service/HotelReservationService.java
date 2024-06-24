package SpringBootStarterProject.HotelReservationPackage.Service;

import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservation;
import SpringBootStarterProject.HotelReservationPackage.Repository.HotelReservationRepository;
import SpringBootStarterProject.HotelReservationPackage.Request.HotelReservationRequest;
import SpringBootStarterProject.HotelReservationPackage.Response.HotelReservationResponse;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static SpringBootStarterProject.HotelReservationPackage.Enum.HotelReservationStatus.*;

@Service
@RequiredArgsConstructor
public class HotelReservationService {

    private final HotelReservationRepository hotelReservationRepository;
    private final ObjectsValidator<HotelReservationRequest> validator;
    private final HotelRepository hotelRepository;
    private final ClientRepository clientRepository;


    public ApiResponseClass createReservation(HotelReservationRequest request){
        validator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
        Integer totalNum = hotel.getNum_of_rooms();

        HotelReservation reservation = HotelReservation.builder()
                .hotel(hotel)
                .client(client)
                .endDate(request.getEndDate())
                .startDate(request.getStartDate())
                .status(CREATED.name())
//                .totalPrice()


                .build();
        hotelReservationRepository.save(reservation);
        HotelReservationResponse response = HotelReservationResponse.builder()
                .id(reservation.getId())
                .hotelName(reservation.getHotel().getName())
                .hotelId(reservation.getHotel().getId())
                .endDate(reservation.getEndDate())
                .startDate(reservation.getStartDate())
                .status(reservation.getStatus())

                .build();
        return new ApiResponseClass("Created successfully, waiting for accepting",HttpStatus.CREATED,LocalDateTime.now(),reservation);
    }



    public ApiResponseClass startingReservation(Integer reservationId){
        HotelReservation reservation = hotelReservationRepository.findById(reservationId).orElseThrow(()->new RequestNotValidException("Reservation Not Found"));
        if(reservation.getStatus().equals(FINISHED.name())){
            return new ApiResponseClass("Reservation finished",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        if(reservation.getStatus().equals(CANCELLED.name())){
            return new ApiResponseClass("Reservation already cancelled",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
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




}
