package SpringBootStarterProject.Trip_ReservationPackage.Controller;

import SpringBootStarterProject.Trip_ReservationPackage.Request.PassengerDetailsRequest;
import SpringBootStarterProject.Trip_ReservationPackage.Service.Reserve_In_Trip_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Reserve_In_Trip")
public class Reserve_In_Trip_Controller {
    private final Reserve_In_Trip_Service reserveService;

    @PostMapping("/{trip_Id}")
    private ResponseEntity<?> Reserve_In_Trip(@PathVariable Integer trip_Id,@RequestBody List<PassengerDetailsRequest> PassengerRequest )
    {
      return ResponseEntity.ok(reserveService.ReserveInTrip(trip_Id,PassengerRequest));
    }
    @PostMapping("/Add_to_Existing_Reservation/{trip_Id}/")
    private ResponseEntity<?> Add_Passengers_To_Existing_Reservation(@PathVariable Integer trip_Id,@RequestBody List<PassengerDetailsRequest> PassengerRequest )
    {
        return ResponseEntity.ok(reserveService.Add_Passengers_To_Existing_Reservation(trip_Id,PassengerRequest));
    }

    @PutMapping("/{reservation_Id}/{passenger_Id}")
    private ResponseEntity<?> Updated_Reserved_Passengers(@PathVariable Integer reservation_Id,@PathVariable Integer passenger_Id,PassengerDetailsRequest request )
    {
        return ResponseEntity.ok(reserveService.Updated_Reserved_Passengers(reservation_Id,passenger_Id,request));
    }


    @GetMapping("/GetAll")
    private ResponseEntity<?> GetMyPassenger(Integer Trip_Id , Pageable pageable)
    {
        return ResponseEntity.ok(reserveService.GetMyPassenger(Trip_Id));
    }

    @DeleteMapping("/{Passenger_Id}")
    private ResponseEntity<?> DeletePassengerReservation(@PathVariable Integer Passenger_Id )
    {
        return ResponseEntity.ok(reserveService.DeletePassengerReservation(Passenger_Id));
    }
}