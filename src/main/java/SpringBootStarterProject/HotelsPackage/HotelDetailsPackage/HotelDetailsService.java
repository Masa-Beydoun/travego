package SpringBootStarterProject.HotelsPackage.HotelDetailsPackage;

import SpringBootStarterProject.HotelsPackage.Hotel.Hotel;
import SpringBootStarterProject.HotelsPackage.Hotel.HotelService;

import SpringBootStarterProject.HotelsPackage.Request.HotelDetailsRequest;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ResourcesPackage.FileEntity;
import SpringBootStarterProject.ResourcesPackage.FileService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static SpringBootStarterProject.ResourcesPackage.ResourceType.HOTEL_DETAILS;

@Service
@RequiredArgsConstructor()
public class HotelDetailsService {

    private final HotelDetailsRepository hotelDetailsRepository;
    private final HotelService hotelService;
    private final FileService fileService;

    private final ObjectsValidator<HotelDetailsRequest> hotelDetailsRequestObjectsValidator;



    public HotelDetails getHotelDetailsById(Integer id){
        return hotelDetailsRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
    }


    public HotelDetails save(HotelDetailsRequest request) {

        hotelDetailsRequestObjectsValidator.validate(request);

        Hotel hotel = hotelService.findHotelById(request.getHotel().getId());

        List<FileEntity> savedPhotos = new ArrayList<>();
        List<MultipartFile> requestedPhotos = request.getPhotos();
        List<Integer> saved_photos_ids = new ArrayList<>();
        for(MultipartFile resource : requestedPhotos){
            FileEntity savedPhoto = fileService.saveFile(resource);
            savedPhotos.add(savedPhoto);
            saved_photos_ids.add(savedPhoto.getId());
        }



        HotelDetails hotelDetails = HotelDetails.builder()
                .breakfastPrice(request.getBreakfastPrice())
                .distanceFromCity(request.getDistanceFromCity())
                .priceForExtraBed(request.getPriceForExtraBed())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .hotel(hotel)
                .photos(saved_photos_ids)
                .build();

        for(FileEntity resource : savedPhotos){
            fileService.update(resource,HOTEL_DETAILS,hotelDetails.getId());
        }


        hotelService.updateWithDetails(hotelDetails);
        return hotelDetailsRepository.save(hotelDetails);



    }




}
