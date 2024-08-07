package SpringBootStarterProject.HotelsPackage.Repository;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.Trippackage.Models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    public List<Hotel> findAllByCityId(Integer cityId);
    public List<Hotel> findAllByCountryId(Integer countryId);
//    public Optional<List<Hotel>> findByName(String name);
    public Optional<Hotel> findByName(String name);



    @Query("SELECT h FROM Hotel h WHERE h.name LIKE %:searchTerm%")
    List<Hotel> findBySearchTerm(@Param("searchTerm") String searchTerm);

}
