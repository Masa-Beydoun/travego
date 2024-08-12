package SpringBootStarterProject.TripReservationPackage.Repository;

import SpringBootStarterProject.TripReservationPackage.Models.PassengerDetails;
import SpringBootStarterProject.TripReservationPackage.Models.PassengerDetails;
import SpringBootStarterProject.TripReservationPackage.Models.TripReservation;
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
public interface Passenger_Details_Repository extends JpaRepository<PassengerDetails,Integer> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM ConfirmationPassengersDetails cd " +
            "JOIN cd.tripReservation res " +
            "JOIN res.passengerDetails p " +
            "JOIN res.trip tr " +
            "WHERE p.firstname = :firstname " +
            "AND p.lastname = :lastname " +
            "AND p.fathername = :fathername " +
            "AND p.mothername = :mothername " +
            "AND p.birthdate = :birthdate " +
            "AND tr.id = :tripId")
    boolean existsByDetailsAndTripId(
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            @Param("fathername") String fathername,
            @Param("mothername") String mothername,
            @Param("birthdate") LocalDate birthdate,
            @Param("tripId") Integer tripId);

    @Query("""
    SELECT t FROM TripReservation p
    LEFT JOIN p.passengerDetails t
    WHERE t.clientId = :clientId AND p.trip.id = :trip_id
    """)
    Page<PassengerDetails> findPassengersAddedByMeToThisTrip(@Param("trip_id") Integer tripId, @Param("clientId") Integer clientId, Pageable pageable);


}
