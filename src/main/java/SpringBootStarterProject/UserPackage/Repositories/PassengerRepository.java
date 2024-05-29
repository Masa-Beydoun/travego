package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger,Integer> {
   Optional<Passenger> findPassengerByUniqueName (String full_name);
  List<Passenger> findPassengersByClientId (Integer client);

}
