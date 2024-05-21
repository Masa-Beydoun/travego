package SpringBootStarterProject.HotelsPackage.HotelDetails;

import SpringBootStarterProject.HotelsPackage.Request.HotelDetailsRequest;
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
    public ResponseEntity<HotelDetails> createHotelDetails(@RequestBody HotelDetailsRequest request) {
        return ResponseEntity.ok(hotelDetailsService.save(request));
    }

    @PutMapping
    public ResponseEntity<HotelDetails> updateHotelDetails(@RequestBody HotelDetailsRequest request) {
        return ResponseEntity.ok(hotelDetailsService.save(request));
    }



}
