package SpringBootStarterProject.HotelReservationPackage.Service;

import SpringBootStarterProject.HotelReservationPackage.Enum.AcceptingStatus;
import SpringBootStarterProject.HotelReservationPackage.Model.HotelConfirmationPassengersDetails;
import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservation;
import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservationPassengerDetails;
import SpringBootStarterProject.HotelReservationPackage.Model.RoomReservation;
import SpringBootStarterProject.HotelReservationPackage.Repository.HotelConfirmationPassengersDetailsRepository;
import SpringBootStarterProject.HotelReservationPackage.Repository.HotelReservationPassengerDetailsRepository;
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
import SpringBootStarterProject.HotelReservationPackage.Request.AcceptHotelReservationPassengerRequest;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ReservationConfirmPackage.Model.ConfirmReservation;
import SpringBootStarterProject.ReservationConfirmPackage.Repository.ConfirmReservationRepository;
import SpringBootStarterProject.TripReservationPackage.Request.PassengerDetailsRequest;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ObjectsValidator<PassengerDetailsRequest> passengerDetailsValidator;
    private final HotelReservationPassengerDetailsRepository hotelReservationPassengerDetailsRepository;
    private final HotelConfirmationPassengersDetailsRepository hotelConfirmationPassengersDetailsRepository;



    public ApiResponseClass createReservation(HotelReservationRequest request){
        validator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));


        List<PassengerDetailsRequest> passengerDetailsRequests = request.getPassengerRequest();
        for (PassengerDetailsRequest passengerRequest : passengerDetailsRequests) {
            passengerDetailsValidator.validate(passengerRequest);
        }
        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
        HotelDetails details = hotelDetailsRepository.findByHotelId(hotel.getId()).orElseThrow(()-> new RequestNotValidException("Hotel Details not found"));

        //TODO : check if there is enough room
        List<RoomReservationRequest> roomReservationRequests = request.getRoomReservations();
        List<HotelReservation> hotelReservations = hotelReservationRepository.findAllByHotelIdAndStartDateAfterAndEndDateBefore(details.getId(),request.getStartDate(),request.getEndDate()).orElse(null);
        if(hotelReservations != null){
            for(HotelReservation previousHotelReservation : hotelReservations){
                List<RoomReservation> previousReservations = previousHotelReservation.getRoomReservations();

                for(RoomReservation roomReservation : previousReservations){
                    Integer cnt=0;
                    for(RoomReservationRequest request1 : roomReservationRequests){
                        if(roomReservation.getRoom().getId().equals(request1.getRoomId())){
                            cnt+=roomReservation.getNumberOfRooms();
                        }
                    }
                    if(cnt+roomReservation.getNumberOfRooms()>roomReservation.getRoom().getTotalNumberOfRooms()){
                        return  new ApiResponseClass("Reservation can't be made, No enough Rooms",HttpStatus.BAD_REQUEST,LocalDateTime.now());
                    }
                }
            }
        }

        //create room reservation
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
                .status(CREATED)
                .totalPrice(totalPrice)
                .build();
        hotelReservationRepository.save(reservation);




        List<HotelReservationPassengerDetails> PassengerArray = new ArrayList<>();
        for (PassengerDetailsRequest passengerRequest : passengerDetailsRequests) {
            if (!hotelReservationPassengerDetailsRepository.findAllByHotelReservationIdAndFirstNameAndLastnameAndFatherNameAndMotherNameAndBirthdate
                    (reservation.getId(),passengerRequest.getFirstname(),
                            passengerRequest.getLastname(), passengerRequest.getFathername(),
                            passengerRequest.getMothername(), passengerRequest.getBirthdate())) {
            HotelReservationPassengerDetails passenger = HotelReservationPassengerDetails.builder()
                        .clientId(client.getId())
                        .hotelReservation(reservation)
                        .firstName(passengerRequest.getFirstname())
                        .lastname(passengerRequest.getLastname())
                        .fatherName(passengerRequest.getFathername())
                        .motherName(passengerRequest.getMothername())
                        .birthdate(passengerRequest.getBirthdate())
                        .nationality(passengerRequest.getNationality())
                        .personalIdentity_PHOTO(passengerRequest.getPersonalIdentity_PHOTO())
                        .passport_issue_date(passengerRequest.getPassport_Issue_date())
                        .passport_expires_date(passengerRequest.getPassport_Expires_date())
                        .passport_number(passengerRequest.getPassport_Number())
                        .passport_PHOTO(passengerRequest.getPassport_PHOTO())
                        .visa_Type(passengerRequest.getVisa_Type())
                        .visa_Country(passengerRequest.getVisa_Country())
                        .visa_issue_date(passengerRequest.getVisa_Issue_date())
                        .visa_expires_date(passengerRequest.getVisa_Expires_date())
                        .visa_PHOTO(passengerRequest.getVisa_PHOTO())
                        .build();

                hotelReservationPassengerDetailsRepository.save(passenger);
                PassengerArray.add(passenger);

                HotelConfirmationPassengersDetails confPassengerDetails = HotelConfirmationPassengersDetails.builder()
                        .passenger_details_id(passenger)
                        .User_email(client.getEmail())
                        .status(AcceptingStatus.PENDING)
                        .description("PENDING")
                        .hotelReservation(reservation)
                        .build();
                passenger.setHotelConfirmationPassengersDetails(confPassengerDetails);

            }
            else
                throw new IllegalStateException("Passenger With Name " + passengerRequest.getFirstname()+" "+passengerRequest.getFathername()+" "+passengerRequest.getLastname()+ " Already Reserved In This Hotel Reservation With id " + reservation.getId());
        }
        reservation.setPassengerDetails(PassengerArray);


        //Response part
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
                .passengerDetails(reservation.getPassengerDetails())
                .build();


        return new ApiResponseClass("Created successfully, waiting for accepting",HttpStatus.CREATED,LocalDateTime.now(),response);
    }



    public ApiResponseClass startingReservation(Integer reservationId){
        HotelReservation reservation = hotelReservationRepository.findById(reservationId).orElseThrow(()->new RequestNotValidException("Reservation Not Found"));
        if(reservation.getStatus().equals(FINISHED)){
            return new ApiResponseClass("Reservation finished",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        if(reservation.getStatus().equals(CANCELLED)){
            return new ApiResponseClass("Reservation already cancelled",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        ConfirmReservation confirmReservation = confirmReservationRepository.findByReservationId(reservationId).orElseThrow(()->new RequestNotValidException("Reservation is rejected by "));
        LocalDate today = LocalDate.now();
        if(reservation.getStartDate().isAfter(LocalDate.now()) && !reservation.getStatus().equals(DURING)){
            reservation.setStatus(DURING);
            return new ApiResponseClass("Reservation Started now", HttpStatus.OK,LocalDateTime.now());
        }

        return new ApiResponseClass("nothing to update",HttpStatus.OK, LocalDateTime.now());

    }


    public ApiResponseClass acceptHotelReservation(Integer reservationId) {
        HotelReservation reservation = hotelReservationRepository.findById(reservationId).orElseThrow(()->new RequestNotValidException("Reservation Not Found"));
        if(reservation.getStatus().equals(FINISHED)){
            return new ApiResponseClass("Reservation finished",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        if(reservation.getStatus().equals(CANCELLED)){
            return new ApiResponseClass("Reservation cancelled",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        reservation.setStatus(ACCEPTED);
        hotelReservationRepository .save(reservation);
        return new ApiResponseClass("Hotel reservation cancelled",HttpStatus.OK, LocalDateTime.now());

    }




    public ApiResponseClass cancelReservation(Integer reservationId){

        HotelReservation reservation = hotelReservationRepository.findById(reservationId).orElseThrow(()->new RequestNotValidException("Reservation Not Found"));
        if(reservation.getStatus().equals(FINISHED)){
            return new ApiResponseClass("Reservation finished",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        if(reservation.getStatus().equals(CANCELLED)){
            return new ApiResponseClass("Reservation already cancelled",HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        LocalDate today = LocalDate.now();
        if(reservation.getStartDate().isAfter(LocalDate.of(today.getYear(),today.getMonthValue(),today.getDayOfMonth()+ 2))){
            return new ApiResponseClass("Reservation can't be Cancelled", HttpStatus.OK,LocalDateTime.now());
        }
        reservation.setStatus(CANCELLED);
        hotelReservationRepository.save(reservation);
        return new ApiResponseClass("Hotel reservation cancelled",HttpStatus.OK, LocalDateTime.now());

    }






    public ApiResponseClass deleteReservation(Integer id) {
        HotelReservation reservation = hotelReservationRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Reservation Not found"));
        hotelReservationRepository.delete(reservation);
        return new ApiResponseClass("Hotel reservation deleted",HttpStatus.OK, LocalDateTime.now());
    }



    public Integer totalDay(LocalDate beginDate, LocalDate endDate){

        Integer yearDif = endDate.getYear() - beginDate.getYear();
        Integer monthDif = endDate.getMonthValue() - beginDate.getMonthValue();
        Integer dayDif = endDate.getDayOfMonth() - beginDate.getDayOfMonth();

        return dayDif + 30*monthDif + 365*yearDif;

    }

    public ApiResponseClass getAllMyReservation(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));

        List<HotelReservation> hotelReservations = hotelReservationRepository.findAllByClientId(client.getId()).orElse(null);

        if(hotelReservations == null){
            return new ApiResponseClass("No reservations found", HttpStatus.OK, LocalDateTime.now());
        }
        //we have all the reservations made by this user
        List<HotelReservationResponse> responses = new ArrayList<>();

        for(HotelReservation hotelReservation : hotelReservations){

            if(hotelReservation.getEndDate().isBefore(LocalDate.now()) && hotelReservation.getStatus()!=CANCELLED){
                hotelReservation.setStatus(FINISHED);
                hotelReservationRepository.save(hotelReservation);
            }
            if(hotelReservation.getEndDate().isAfter(LocalDate.now()) && hotelReservation.getStartDate().isBefore(LocalDate.now())){
                hotelReservation.setStatus(DURING);
                hotelReservationRepository.save(hotelReservation);
            }


            List<RoomReservationResponse> roomReservationResponse2=new ArrayList<>();
            List<RoomReservation> roomReservations = hotelReservation.getRoomReservations();

            for(RoomReservation roomReservation : roomReservations){
                RoomReservation  r1 = roomReservationRepository.findById(roomReservation.getId()).orElseThrow(()-> new RequestNotValidException("Room Reservation Not Found"));

                RoomReservationResponse roomResponse = RoomReservationResponse.builder()
                        .id(r1.getId())
                        .hotelReservationId(r1.getHotelReservationId())
                        .priceForOneRoom(r1.getPriceForOneRoom())
                        .totalPrice(r1.getTotalPrice())
                        .notes(r1.getNotes())
                        .totalExtraBed(r1.getTotalExtraBed())
                        .numberOfRooms(r1.getNumberOfRooms())
                        .roomId(r1.getRoom().getId())
                        .build();
                roomReservationResponse2.add(roomResponse);
            }

            HotelReservationResponse r1 = HotelReservationResponse.builder()
                    .id(hotelReservation.getId())
                    .hotelName(hotelReservation.getHotel().getName())
                    .hotelId(hotelReservation.getHotel().getId())
                    .endDate(hotelReservation.getEndDate())
                    .startDate(hotelReservation.getStartDate())
                    .status(hotelReservation.getStatus())
                    .roomReservationResponses(roomReservationResponse2)
                    .clientId(hotelReservation.getClient().getId())
                    .totalPrice(hotelReservation.getTotalPrice())
                    .build();
            responses.add(r1);
        }

        return  new ApiResponseClass("Reservations Got Successfully",HttpStatus.OK,LocalDateTime.now(),responses);

    }

    public ApiResponseClass acceptHotelReservationPassenger(AcceptHotelReservationPassengerRequest request) {

        HotelReservationPassengerDetails passenger =  hotelReservationPassengerDetailsRepository.findById(request.getHotelReservationPassengerDetailsId()).orElseThrow(()-> new RequestNotValidException("Passenger Not found"));
        HotelReservation reservation = hotelReservationRepository.findById(request.getHotelReservationId()).orElseThrow(()-> new RequestNotValidException("Reservation Not found"));
        HotelConfirmationPassengersDetails confirm = hotelConfirmationPassengersDetailsRepository.findById(passenger.getHotelConfirmationPassengersDetails().getId()).orElse(null);
        if(confirm == null){

            HotelConfirmationPassengersDetails confPassengerDetails = HotelConfirmationPassengersDetails.builder()
                    .passenger_details_id(passenger)
                    .User_email(reservation.getClient().getEmail())
                    .status(AcceptingStatus.PENDING)
                    .description("PENDING")
                    .hotelReservation(reservation)
                    .build();
            passenger.setHotelConfirmationPassengersDetails(confPassengerDetails);

        }

        hotelConfirmationPassengersDetailsRepository.save(confirm);

         return new ApiResponseClass("The status changed successfully",HttpStatus.OK,LocalDateTime.now(),confirm);

    }
}
