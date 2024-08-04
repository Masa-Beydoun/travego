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
import java.util.Optional;

@Repository
public interface HotelReservationPassengerDetailsRepository extends JpaRepository<HotelReservationPassengerDetails,Integer> {

    boolean existsByFirstNameAndLastnameAndFatherNameAndMotherNameAndBirthdate(String firstName, String lastName, String fatherName, String motherName, LocalDate birthdate);

    Optional<HotelReservationPassengerDetails> findByHotelReservationIdAndId(Integer hotelOd, Integer Passenger_id);

    Page<HotelReservationPassengerDetails> getByHotelReservationIdAndClientId(Integer hotelId, Integer User_Id, Pageable pageable);


    Optional<HotelReservationPassengerDetails> findAllByHotelReservationIdAndFirstNameAndLastnameAndFatherNameAndMotherNameAndBirthdate(Integer hotelReservationId,String firstName, String lastName, String fatherName, String motherName, LocalDate birthdate);


    @Query("""
    SELECT p FROM HotelConfirmationPassengersDetails p
    LEFT JOIN p.hotelReservation t
    WHERE p.clientId = :clientId AND t.id = :hotel_id
    """)
    Page<Passenger_Details> findPassengersAddedByMeToThisHotelReservation(@Param("hotel_id") Integer tripId, @Param("clientId") Integer clientId, Pageable pageable);

}
