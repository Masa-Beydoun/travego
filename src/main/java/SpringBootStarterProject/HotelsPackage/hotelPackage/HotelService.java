package SpringBootStarterProject.HotelsPackage.hotelPackage;

import SpringBootStarterProject.HotelsPackage.HotelDetails.HotelDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {


    @Autowired
    private final HotelRepository hotelRepository;


    public Hotel findHotelById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(()-> new RuntimeException("Hotel not found"));

    }


    public Hotel update(HotelDetails hotelDetails) {
        Hotel hotel = hotelRepository.findById(hotelDetails.getHotel().getId()).orElseThrow(() -> new RuntimeException("Hotel not found"));
        hotel.setHotelDetails(hotelDetails);
        return hotelRepository.save(hotel);
    }






}
