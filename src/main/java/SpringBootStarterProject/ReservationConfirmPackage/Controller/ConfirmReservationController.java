package SpringBootStarterProject.ReservationConfirmPackage.Controller;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ReservationConfirmPackage.Enum.ReservationType;
import SpringBootStarterProject.ReservationConfirmPackage.Request.ConfirmReservationRequest;
import SpringBootStarterProject.ReservationConfirmPackage.Service.ConfirmReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/confirm-reservation")
@RequiredArgsConstructor
@Tag(name = "Confirm Reservation")
public class ConfirmReservationController {
    private final ConfirmReservationService confirmReservationService;

    @PostMapping("/hotel-reservation")
    public ApiResponseClass confirmHotelReservation(@RequestBody ConfirmReservationRequest request) {
        return confirmReservationService.confirmReservation(request, ReservationType.HOTEL);
    }

    @PostMapping("/trup-reservation")
    public ApiResponseClass confirmTripReservation(@RequestBody ConfirmReservationRequest request) {
        return confirmReservationService.confirmReservation(request, ReservationType.TRIP);
    }


}
