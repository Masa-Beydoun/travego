package SpringBootStarterProject.Trip_ReservationPackage.Repository;

import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface Passenger_Details_Repository extends JpaRepository<Passenger_Details,Integer> {

    boolean existsByFisrtnameAndLastnameAndFathernameAndMothernameAndBitrhdate(String fisrtname, String lastname, String fathername, String mothername, LocalDate bitrhdate);
}
