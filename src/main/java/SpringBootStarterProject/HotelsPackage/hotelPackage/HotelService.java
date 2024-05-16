package SpringBootStarterProject.HotelsPackage.hotelPackage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {


    @Autowired
    private final HotelRepository hotelRepository;


    public Hotel findHotelById(Integer id) {
        return hotelRepository.findById(id).orElse(null);

    }


}
