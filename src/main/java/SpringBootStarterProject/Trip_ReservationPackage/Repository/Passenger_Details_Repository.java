package SpringBootStarterProject.Trip_ReservationPackage.Repository;

import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import SpringBootStarterProject.Trip_ReservationPackage.Models.TripReservation;
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
public interface Passenger_Details_Repository extends JpaRepository<Passenger_Details,Integer> {

    boolean existsByFisrtnameAndLastnameAndFathernameAndMothernameAndBitrhdate(String fisrtname, String lastname, String fathername, String mothername, LocalDate bitrhdate);

    Optional<Passenger_Details> findByTripReservation_IdAndId(Integer Reservastion_id, Integer Passenger_id);
    Page<Passenger_Details> getPassenger_DetailsByTripReservationIdAndClientId(Integer Reservation_Id, Integer User_Id, Pageable pageable);


  //  Page<Passenger_Details> getAllByTripReservationIdAndClientId(Integer Reservation_Id, Integer User_Id, Pageable pageable);


    @Query("""
    SELECT p FROM Passenger_Details p
    LEFT JOIN p.tripReservation t
    WHERE p.clientId = :clientId AND t.id = :trip_id
    """)
    Page<Passenger_Details> findPassengersAddedByMeToThisTrip(@Param("trip_id") Integer tripId, @Param("clientId") Integer clientId, Pageable pageable);

}
