package SpringBootStarterProject.ManagingPackage.Search;

import SpringBootStarterProject.City_Place_Package.Service.PlaceService;
import SpringBootStarterProject.HotelsPackage.Service.HotelService;
import SpringBootStarterProject.Trippackage.Service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/searching")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SearchController {
    @Autowired
    private TripService tripService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private PlaceService placeService;

    @GetMapping("/by-trip/{searchTerm}")
    public ResponseEntity<?> getTripsByChar(@PathVariable String searchTerm) {
        return ResponseEntity.ok(tripService.searchByCharOfName(searchTerm));
    }

    @GetMapping("/by-hotel/{searchTerm}")
    public ResponseEntity<?> getHotelsByChar(@PathVariable String searchTerm) {
        return ResponseEntity.ok(hotelService.searchByCharOfName(searchTerm));
    }

    @GetMapping("by-place/{searchTerm}")
    public ResponseEntity<?> getPlacesByChar(@PathVariable String searchTerm) {
        return ResponseEntity.ok(placeService.searchByCharOfName(searchTerm));
    }

}
