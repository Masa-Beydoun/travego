package SpringBootStarterProject.City_package.Repository;

import SpringBootStarterProject.City_package.Models.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {

    public City findByName(String name);

}
