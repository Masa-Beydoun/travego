package SpringBootStarterProject.HotelReservationPackage.Repository;

import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservationPassengerDetails;
import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelReservationPassengerDetailsRepository extends JpaRepository<HotelReservationPassengerDetails,Integer> {

    boolean findAllByHotelReservationIdAndFirstNameAndLastnameAndFatherNameAndMotherNameAndBirthdate(Integer hotelReservationId, String firstName, String lastName, String fatherName, String motherName, LocalDate birthdate);
}
