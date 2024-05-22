package SpringBootStarterProject.HotelsPackage.Controller;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Service.HotelDetailsService;
import SpringBootStarterProject.HotelsPackage.Request.HotelDetailsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hotel_details")
@RequiredArgsConstructor
public class HotelDetailsController {
    private final HotelDetailsService hotelDetailsService;



    @PostMapping
    public ResponseEntity<HotelDetails> createHotelDetails(@RequestBody HotelDetailsRequest request) {
        return ResponseEntity.ok(hotelDetailsService.save(request));
    }

    @PutMapping
    public ResponseEntity<HotelDetails> updateHotelDetails(@RequestBody HotelDetailsRequest request) {
        return ResponseEntity.ok(hotelDetailsService.save(request));
    }



}
