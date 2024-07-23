package SpringBootStarterProject.SuggestTrip.Controller;

import SpringBootStarterProject.SuggestTrip.Request.SuggestTripRequest;
import SpringBootStarterProject.SuggestTrip.Service.SuggestTripService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/suggest-trip")
@Tag(name = "Suggest-Trip")
@CrossOrigin("*")
public class suggestTripController {

    @Autowired
    private SuggestTripService suggestTripService;

    @GetMapping
    public ResponseEntity<?> getAllSuggestTrips() {
        return ResponseEntity.ok(suggestTripService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getSuggestTripById(@PathVariable Integer id) {
        return ResponseEntity.ok(suggestTripService.findById(id));
    }
    @GetMapping("suggested-by-user/{userId}")
    public ResponseEntity<?> getSuggestTripByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(suggestTripService.findByUserId(userId));
    }
    @PostMapping
    public ResponseEntity<?> createSuggestedTrip(SuggestTripRequest request ){
        return ResponseEntity.status(HttpStatus.CREATED).body(suggestTripService.createSuggestTrip(request));
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateSuggestedTrip(@PathVariable Integer id, SuggestTripRequest request ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(suggestTripService.updateSuggestTrip(request,id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSuggestedTrip(@PathVariable Integer id) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(suggestTripService.deleteSuggestTrip(id));
    }

}
