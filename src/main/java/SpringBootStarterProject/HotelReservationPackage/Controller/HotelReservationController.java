package SpringBootStarterProject.HotelReservationPackage.Controller;

import SpringBootStarterProject.HotelReservationPackage.Request.HotelReservationRequest;
import SpringBootStarterProject.HotelReservationPackage.Service.HotelReservationService;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hotel-reservation")
@RequiredArgsConstructor
@Tag(name = "Hotel Reservation", description = "This controller to manage hotel reservations")
public class HotelReservationController {

    private final HotelReservationService hotelReservationService;

    @PostMapping
    public ApiResponseClass addHotelReservation(@RequestBody HotelReservationRequest hotelReservationRequest) {
        return hotelReservationService.createReservation(hotelReservationRequest);
    }

    @PutMapping("/start-reservation/{id}")
    public ApiResponseClass startingReservation(@PathVariable("id") Integer id) {
        return hotelReservationService.startingReservation(id);
    }

    @PutMapping("/accept-reservation/{id}")
    public ApiResponseClass acceptHotelReservation(@PathVariable("id") Integer id) {
        return hotelReservationService.acceptHotelReservation(id);
    }

    @PutMapping("/cancel-reservation/{id}")
    public ApiResponseClass cancelHotelReservation(@PathVariable("id") Integer id) {
        return hotelReservationService.cancelReservation(id);
    }


    @DeleteMapping("{id}")
    public ApiResponseClass deleteHotelReservation(@PathVariable("id") Integer id) {
        return hotelReservationService.deleteReservation(id);
    }
}
