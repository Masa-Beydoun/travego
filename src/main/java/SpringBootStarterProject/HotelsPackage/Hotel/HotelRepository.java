package SpringBootStarterProject.HotelsPackage.Hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    public List<Hotel> findAllByCityId(Integer cityId);
    public List<Hotel> findAllByCountryId(Integer countryId);

}
