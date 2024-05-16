package SpringBootStarterProject.HotelsPackage.HotelDetails;

import SpringBootStarterProject.HotelsPackage.hotelPackage.Hotel;
import SpringBootStarterProject.HotelsPackage.hotelPackage.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelDetailsService {

    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;
    @Autowired
    private HotelService hotelService;


    public HotelDetails save(HotelDetails hotelDetails) {

        Hotel hotel = hotelService.findHotelById(hotelDetails.getId());
        //edit
        if (hotel == null) {}
        hotelService.update(hotelDetails);
        return hotelDetailsRepository.save(hotelDetails);
    }
}
