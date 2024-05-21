package SpringBootStarterProject.HotelsPackage.HotelPackage;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    public List<Hotel> findAllByCityId(City cityId);
    public List<Hotel> findAllByCountryId(Country countryId);

}
