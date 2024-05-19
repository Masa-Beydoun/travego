package SpringBootStarterProject.City_Place_Package.Repository;

import SpringBootStarterProject.City_Place_Package.Models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    public Optional<Country> findByName(String countryName);
}
