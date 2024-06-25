package SpringBootStarterProject.Trip_ReservationPackage.Controller;

import SpringBootStarterProject.Trip_ReservationPackage.Request.PassengerDetailsRequest;
import SpringBootStarterProject.Trip_ReservationPackage.Service.Reserve_In_Trip_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("Reserve_In_Trip")
public class Reserve_In_Trip_Controller {
    private final Reserve_In_Trip_Service reserveService;

    @PostMapping
    private ResponseEntity<?> Reserve_In_Trip(@RequestBody List<PassengerDetailsRequest> PassengerRequest )
    {
      return ResponseEntity.ok(reserveService.ReserveInTrip(PassengerRequest));
    }

//TODO::: For Edit Reservation
//    @PutMapping
//    private ResponseEntity<?> Reserve_In_Trip(Reserve_In_Trip_Request request )
//    {
//        return ResponseEntity.ok(reserveService.ReserveInTrip(request));
//    }
//
}
