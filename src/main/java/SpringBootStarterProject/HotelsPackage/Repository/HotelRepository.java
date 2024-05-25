package SpringBootStarterProject.HotelsPackage.Repository;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    public List<Hotel> findAllByCityId(City cityId);
    public List<Hotel> findAllByCountryId(Country countryId);
    public Optional<Hotel> findByName(String name);

    public Optional<Hotel> findByName(String name);

}
