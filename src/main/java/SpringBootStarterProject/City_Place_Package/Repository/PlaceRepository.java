package SpringBootStarterProject.City_Place_Package.Repository;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

    public List<Place> findByCityId(Integer cityId);
}
