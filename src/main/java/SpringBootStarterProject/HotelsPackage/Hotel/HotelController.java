package SpringBootStarterProject.HotelsPackage.HotelPackage;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

    @Autowired
    private final HotelService hotelService;

    @GetMapping("/byCityId/{city_id}")
    public ResponseEntity<List<Hotel>> getAllHotelsByCityId(@PathVariable Integer city_id) {
        return ResponseEntity.ok(hotelService.findHotelByCityId(city_id));
    }

    @GetMapping("/byCountryId/{country_id}")
    public ResponseEntity<List<Hotel>> getAllHotelsByCountryId(@PathVariable Integer country_id) {
        return ResponseEntity.ok(hotelService.findHotelByCountryId(country_id));
    }


    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.findAllHotels());
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.save(hotel));
    }

    @PutMapping
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.save(hotel));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteHotel(@RequestBody Hotel hotel) {
        hotelService.delete(hotel);
        return ResponseEntity.noContent().build();
    }



}
