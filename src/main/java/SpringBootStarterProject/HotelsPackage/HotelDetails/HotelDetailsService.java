package SpringBootStarterProject.HotelsPackage.HotelDetails;

import SpringBootStarterProject.HotelsPackage.Hotel.Hotel;
import SpringBootStarterProject.HotelsPackage.Hotel.HotelService;
//import SpringBootStarterProject.HotelsPackage.HotelPackage.Hotel;
//import SpringBootStarterProject.HotelsPackage.HotelPackage.HotelService;
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
        if (hotel == null) { throw new RuntimeException("Hotel not found"); }
        hotelService.updateWithDetails(hotelDetails);
        return hotelDetailsRepository.save(hotelDetails);
    }
}
