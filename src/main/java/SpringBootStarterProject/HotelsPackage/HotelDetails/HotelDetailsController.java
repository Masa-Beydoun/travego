package SpringBootStarterProject.HotelsPackage.HotelDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hotel_details")
public class HotelDetailsController {
    @Autowired
    private HotelDetailsService hotelDetailsService;



    @PostMapping
    public ResponseEntity<HotelDetails> createHotelDetails(@RequestBody HotelDetails hotelDetails) {
        return ResponseEntity.ok(hotelDetailsService.save(hotelDetails));
    }

    @PutMapping
    public ResponseEntity<HotelDetails> updateHotelDetails(@RequestBody HotelDetails hotelDetails) {
        return ResponseEntity.ok(hotelDetailsService.save(hotelDetails));
    }



}
