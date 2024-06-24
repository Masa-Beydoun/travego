package SpringBootStarterProject.ReservationConfirmPackage.Repository;

import SpringBootStarterProject.ReservationConfirmPackage.Enum.ReservationType;
import SpringBootStarterProject.ReservationConfirmPackage.Model.ConfirmReservation;
import SpringBootStarterProject.UserPackage.Models.Manager;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmReservationRepository extends JpaRepository<ConfirmReservation, Integer> {



}
