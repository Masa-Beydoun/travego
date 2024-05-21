package SpringBootStarterProject.HotelsPackage.HotelDetailsPackage;

import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelDetailsRepository extends JpaRepository<HotelDetails, Integer> {
}
