package SpringBootStarterProject.Trip_ReservationPackage.Repository;

import SpringBootStarterProject.Trip_ReservationPackage.Models.ConfirmationPassengersDetails;
import SpringBootStarterProject.Trip_ReservationPackage.Models.ConfirmationPassengersDetailsDto;
import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import SpringBootStarterProject.Trip_ReservationPackage.Models.TripReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationPassengerDetailsRepository extends JpaRepository<ConfirmationPassengersDetails,Integer> {

    @Query("""
SELECT c FROM ConfirmationPassengersDetails c
JOIN c.tripReservation t
JOIN t.trip trip
WHERE trip.id= :tripId
""")
    Page<ConfirmationPassengersDetails> getAllPassengerDetailsRequestByTripId(@Param("tripId") Integer tripId, Pageable pageable);


//    @Query("""
//SELECT p,p.tripReservation.trip.id,p.tripReservation.id FROM Passenger_Details p
//JOIN p.tripReservation t
//JOIN t.trip trip
//WHERE trip.id = :tripId
//""")
//    List<Passenger_Details> getAllPassengerDetailsByTripId(@Param("tripId") Integer tripId);
//
//    @Query("""
//    SELECT c FROM ConfirmationPassengersDetails c
//    JOIN c.tripReservation t
//    WHERE t.id = :tripReservationId
//    """)
//    ConfirmationPassengersDetails findByTripReservationId(@Param("tripReservationId") Integer tripReservationId);
//    @Query("""
//    SELECT p,p.tripReservation.trip.id,p.tripReservation.id
//    FROM Passenger_Details p
//    WHERE p.tripReservation.id = :resId
//    """)
//    Passenger_Details findByTripReservation_Id(@Param("resId") Integer resId);


    //TODO :: EDIT THE QUEREY To Get confirmation related to Trip_Id Then get all passengers details
    @Query("""
SELECT c FROM ConfirmationPassengersDetails c
JOIN c.tripReservation tr
JOIN tr.trip t
WHERE t.id=:tripId
""")
   List <ConfirmationPassengersDetails> GetTripReservationRelatedToTripId(@Param("tripId") Integer tripId);


}
