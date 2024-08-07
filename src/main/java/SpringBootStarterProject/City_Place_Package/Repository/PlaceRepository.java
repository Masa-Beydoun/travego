package SpringBootStarterProject.City_Place_Package.Repository;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

    public List<Place> findByCityId(Integer cityId);


    @Query("SELECT p FROM Place p WHERE p.name LIKE %:searchTerm%")
    List<Place> findBySearchTerm(@Param("searchTerm") String searchTerm);

}
