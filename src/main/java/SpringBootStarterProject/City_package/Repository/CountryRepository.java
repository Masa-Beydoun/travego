package SpringBootStarterProject.City_package.Repository;

import SpringBootStarterProject.City_Place_Package.Models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
