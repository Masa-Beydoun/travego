package SpringBootStarterProject.SuggestTrip.Controller;

import SpringBootStarterProject.SuggestTrip.Service.SuggestTripService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/suggest-trip")
@Tag(name = "Suggest-Trip")
public class suggestTripController {

    @Autowired
    private SuggestTripService suggestTripService;

    @GetMapping
    public ResponseEntity<?> getAllSuggestTrips() {
        return ResponseEntity.ok(suggestTripService.findAll());
    }

//    @GetMapping
//    public ResponseEntity<?> getSuggestTripById(@RequestParam int id) {
//
//    }

}
