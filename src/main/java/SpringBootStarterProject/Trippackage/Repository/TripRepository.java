package SpringBootStarterProject.Trippackage.Repository;

import SpringBootStarterProject.Trippackage.Enum.TripCategory;
import SpringBootStarterProject.Trippackage.Enum.TripStatus;
import SpringBootStarterProject.Trippackage.Models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {

     List<Trip> findByIsPrivateFalse();


     List<Trip> findByTripCategory(TripCategory tripCategory);

     List<Trip> findByStatus(TripStatus tripStatus);

     @Query("SELECT t FROM Trip t WHERE t.name LIKE %:searchTerm%")
     List<Trip> findBySearchTerm(@Param("searchTerm") String searchTerm);

//    List<Trip> findAll(TripSpecification specification);
}
