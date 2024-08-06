package SpringBootStarterProject.Trippackage.Repository;

import SpringBootStarterProject.Trippackage.Enum.TripCategory;
import SpringBootStarterProject.Trippackage.Models.Trip;
import SpringBootStarterProject.Trippackage.Service.TripSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {

     List<Trip> findByIsPrivateFalse();


     List<Trip> findByTripCategory(TripCategory tripCategory);

//    List<Trip> findAll(TripSpecification specification);
}
