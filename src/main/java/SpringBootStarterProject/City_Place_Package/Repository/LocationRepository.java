package SpringBootStarterProject.City_Place_Package.Repository;


import SpringBootStarterProject.City_Place_Package.Models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository  extends JpaRepository<Location,Integer> {
}
