package SpringBootStarterProject.SuggestTrip.Controller;

import SpringBootStarterProject.SuggestTrip.Request.SuggestTripRequest;
import SpringBootStarterProject.SuggestTrip.Service.SuggestTripService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController()
@RequestMapping("api/v1/suggest-trip")
@RequiredArgsConstructor
@Tag(name = "Suggest-Trip")
public class suggestTripController {

    @Autowired
    private final SuggestTripService suggestTripService;

    @GetMapping
    public ResponseEntity<?> getAllSuggestTrips() {
        return ResponseEntity.ok(suggestTripService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getSuggestTripById(@PathVariable Integer id) {
        return ResponseEntity.ok(suggestTripService.findById(id));
    }
    @GetMapping("{userId}")
    public ResponseEntity<?> getSuggestTripByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(suggestTripService.findByUserId(userId));
    }
    @PostMapping
    public ResponseEntity<?> createSuggestedTrip(SuggestTripRequest request , Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(suggestTripService.createSuggestTrip(request,principal));
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateSuggestedTrip(@PathVariable Integer id, SuggestTripRequest request , Principal principal) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(suggestTripService.updateSuggestTrip(request,principal,id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSuggestedTrip(@PathVariable Integer id) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(suggestTripService.deleteSuggestTrip(id));
    }

}
