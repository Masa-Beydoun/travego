package SpringBootStarterProject.HotelsPackage.HotelPackage;

import SpringBootStarterProject.HotelsPackage.HotelDetails.HotelDetails;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    @Autowired
    private final HotelRepository hotelRepository;

    public Hotel findHotelById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(()-> new RuntimeException("Hotel not found"));
    }

    public List<Hotel> findHotelByCityId(Integer id) {
        List<Hotel> hotels = hotelRepository.findAllByCityId(id);
        if(hotels.isEmpty()) throw new RuntimeException("No Hotels found");
        return hotels;
    }

    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    public List<Hotel> findHotelByCountryId(Integer id) {
        List<Hotel> hotels = hotelRepository.findAllByCountryId(id);
        if(hotels.isEmpty()) throw new RuntimeException("No Hotels found");
        return hotels;
    }

    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateWithDetails(@NotNull HotelDetails hotelDetails) {
        Hotel hotel = hotelRepository.findById(hotelDetails.getHotel().getId()).orElseThrow(() -> new RuntimeException("Hotel not found"));
        hotel.setHotelDetails(hotelDetails);
        return hotelRepository.save(hotel);
    }


    public void delete(@NotNull Hotel hotel) {
        Hotel hotel1=hotelRepository.findById(hotel.getId()).orElseThrow(() -> new RuntimeException("Hotel not found"));
        hotelRepository.delete(hotel);
    }







}
