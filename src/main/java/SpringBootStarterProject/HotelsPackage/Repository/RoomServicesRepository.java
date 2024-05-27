package SpringBootStarterProject.HotelsPackage.Repository;

import SpringBootStarterProject.HotelsPackage.Models.Room;
import SpringBootStarterProject.HotelsPackage.Models.RoomServices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomServicesRepository extends JpaRepository<RoomServices, Integer> {

    public RoomServices findByName(String name);
}
