package SpringBootStarterProject.TripReservationPackage.Controller;

import SpringBootStarterProject.TripReservationPackage.Request.PassengerDetailsRequest;
import SpringBootStarterProject.TripReservationPackage.Service.TripConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/Trip/Confirmation")
public class TripConfirmationController {

    private final TripConfirmationService tripConfirmationService;
    @GetMapping("/GetAll/MyConfirmation")
    private ResponseEntity<?> GetAllMyConfirmation() {
        return ResponseEntity.ok(tripConfirmationService.GetAllMyConfirmation());
    }

    @GetMapping("/GetOne/MyConfirmation/{ConfirmationID}")
    private ResponseEntity<?> GetOneMyConfirmation(@PathVariable Integer ConfirmationID) {
        return ResponseEntity.ok(tripConfirmationService.GetOneMyConfirmation(ConfirmationID));
    }


}