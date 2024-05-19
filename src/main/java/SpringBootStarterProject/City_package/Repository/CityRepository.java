package SpringBootStarterProject.City_package.Repository;

import SpringBootStarterProject.City_package.Models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {

    @Query("SELECT c FROM City  c WHERE c.country.name = :name")
    public List<City> findByCountry(@Param("name") String name);

    public City findByName(String name);
}
