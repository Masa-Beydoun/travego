package SpringBootStarterProject.HotelsPackage.HotelServices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelServicesRepository extends JpaRepository<HotelServices, Integer> {

    public HotelServices findByName(String name);

}
