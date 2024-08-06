package SpringBootStarterProject.HotelReservationPackage.Repository;

import SpringBootStarterProject.HotelReservationPackage.Model.HotelConfirmationPassengersDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelConfirmationPassengersDetailsRepository extends JpaRepository<HotelConfirmationPassengersDetails,Integer> {


}
