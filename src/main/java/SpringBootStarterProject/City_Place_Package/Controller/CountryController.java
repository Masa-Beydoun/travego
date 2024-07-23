package SpringBootStarterProject.City_Place_Package.Controller;


import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.City_Place_Package.Request.CountryRequest;
import SpringBootStarterProject.City_Place_Package.Service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/country")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(countryService.findAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(countryService.findById(id));
    }
    @PostMapping
    public ResponseEntity<?> addNewCountry(@RequestBody CountryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(countryService.addNewCountry(request));
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateCountry(@PathVariable Integer id, @RequestBody CountryRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(countryService.updateCountry(id, request));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(countryService.deleteCountry(id));
    }
}
