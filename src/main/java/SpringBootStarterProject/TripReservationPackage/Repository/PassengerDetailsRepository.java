package SpringBootStarterProject.TripReservationPackage.Repository;

import SpringBootStarterProject.TripReservationPackage.Models.PassengerDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PassengerDetailsRepository extends JpaRepository<PassengerDetails,Integer> {

    boolean existsByFisrtnameAndLastnameAndFathernameAndMothernameAndBitrhdate(String fisrtname, String lastname, String fathername, String mothername, LocalDate bitrhdate);

    Optional<PassengerDetails> findByTripReservation_IdAndId(Integer Reservastion_id, Integer Passenger_id);
    Page<PassengerDetails> getPassenger_DetailsByTripReservationIdAndClientId(Integer Reservation_Id, Integer User_Id, Pageable pageable);


  //  Page<Passenger_Details> getAllByTripReservationIdAndClientId(Integer Reservation_Id, Integer User_Id, Pageable pageable);


    @Query("""
    SELECT p FROM PassengerDetails p
    LEFT JOIN p.tripReservation t
    WHERE p.clientId = :clientId AND t.id = :trip_id
    """)
    Page<PassengerDetails> findPassengersAddedByMeToThisTrip(@Param("trip_id") Integer tripId, @Param("clientId") Integer clientId, Pageable pageable);

}
