package SpringBootStarterProject.ReservationConfirmPackage.Service;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ReservationConfirmPackage.Enum.ReservationStatus;
import SpringBootStarterProject.ReservationConfirmPackage.Enum.ReservationType;
import SpringBootStarterProject.ReservationConfirmPackage.Model.ConfirmReservation;
import SpringBootStarterProject.ReservationConfirmPackage.Repository.ConfirmReservationRepository;
import SpringBootStarterProject.ReservationConfirmPackage.Request.ConfirmReservationRequest;
import SpringBootStarterProject.ReservationConfirmPackage.Response.ConfirmReservationResponse;
import SpringBootStarterProject.UserPackage.Repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConfirmReservationService {

    private final ConfirmReservationRepository confirmReservationRepository;
    private final ObjectsValidator<ConfirmReservationRequest> validator;
    private final ManagerRepository managerRepository;

    public ApiResponseClass confirmReservation(ConfirmReservationRequest request, ReservationType type) {
        validator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var manager = managerRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("manager Not Found"));

        ConfirmReservation confirm = ConfirmReservation.builder()
                .description(request.getDescription())
                .manager(manager)
                .type(type)
                .status(ReservationStatus.ACCEPTED)
                .reservationId(request.getReservationId())
                .build();
        confirmReservationRepository.save(confirm);

        ConfirmReservationResponse response = ConfirmReservationResponse.builder()
                .id(confirm.getId())
                .description(confirm.getDescription())
                .type(confirm.getType().name())
                .reservationId(confirm.getReservationId())
                .managerId(confirm.getManager().getId())
                .status(confirm.getStatus().name())
                .build();
        return new ApiResponseClass("Reservation Confirmed Successfully", HttpStatus.OK, LocalDateTime.now(),response);




    }


    public ApiResponseClass rejectReservation(ConfirmReservationRequest request, ReservationType type) {
        validator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var manager = managerRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("manager Not Found"));

        ConfirmReservation confirm = ConfirmReservation.builder()
                .description(request.getDescription())
                .manager(manager)
                .type(type)
                .reservationId(request.getReservationId())
                .status(ReservationStatus.REJECTED)
                .build();
        confirmReservationRepository.save(confirm);

        ConfirmReservationResponse response = ConfirmReservationResponse.builder()
                .id(confirm.getId())
                .description(confirm.getDescription())
                .type(confirm.getType().name())
                .reservationId(confirm.getReservationId())
                .managerId(confirm.getManager().getId())
                .status(confirm.getStatus().name())
                .build();
        return new ApiResponseClass("Reservation Confirmed Successfully", HttpStatus.OK, LocalDateTime.now(),response);



    }
}
