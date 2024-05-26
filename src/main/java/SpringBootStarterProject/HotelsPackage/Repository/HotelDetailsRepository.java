package SpringBootStarterProject.HotelsPackage.Repository;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelDetailsRepository extends JpaRepository<HotelDetails, Integer> {

    public HotelDetails findByHotelId(Integer hotelId);

}
