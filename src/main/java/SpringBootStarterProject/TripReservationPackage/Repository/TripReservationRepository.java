package SpringBootStarterProject.TripReservationPackage.Repository;

import SpringBootStarterProject.TripReservationPackage.Models.TripReservation;
import SpringBootStarterProject.UserPackage.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.Integer;

import java.util.List;

@Repository
public interface TripReservationRepository extends JpaRepository<TripReservation,Integer > {
    @Transactional
    @Modifying
    @Query("delete from TripReservation t where t.client = ?1")
    int deleteByClient(Client client);

    List<TripReservation> findByClient(Client client);
}
