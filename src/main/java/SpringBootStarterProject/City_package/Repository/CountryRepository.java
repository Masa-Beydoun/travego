package SpringBootStarterProject.City_package.Repository;

import SpringBootStarterProject.City_package.Models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    public Optional<Country> findByName(String countryName);
}
