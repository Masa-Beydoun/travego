package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.Room;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelDetailsRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
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
@RequiredArgsConstructor
public class HotelDetailsService {

    private final HotelDetailsRepository hotelDetailsRepository;
    private final HotelService hotelService;
    private final FileService fileService;
//    private final RoomService roomService;

    private final ObjectsValidator<HotelDetailsRequest> hotelDetailsRequestObjectsValidator;



    public HotelDetails getHotelDetailsById(Integer id){
        return hotelDetailsRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel not found"));
    }


    public HotelDetails save(HotelDetailsRequest request) {

        hotelDetailsRequestObjectsValidator.validate(request);

        HotelResponse hotelResponse = hotelService.findHotelById(request.getHotel().getId());

        List<FileEntity> savedPhotos = new ArrayList<>();
        List<MultipartFile> requestedPhotos = request.getPhotos();
        List<Integer> saved_photos_ids = new ArrayList<>();
        for(MultipartFile resource : requestedPhotos){
            FileEntity savedPhoto = fileService.saveFile(resource);
            savedPhotos.add(savedPhoto);
            saved_photos_ids.add(savedPhoto.getId());
        }
        HotelResponse hotelResponse1 = hotelService.findHotelById(request.getHotel().getId());

        //TODO : room service and saving it in the hotelDetails
//        Room savedRoom = roomService.save(request.getRoom());

        HotelDetails hotelDetails = HotelDetails.builder()
                .breakfastPrice(request.getBreakfastPrice())
                .distanceFromCity(request.getDistanceFromCity())
                .priceForExtraBed(request.getPriceForExtraBed())
                .startTime(request.getStartTime())
                .commentReviews(request.getCommentReviews())
                .reviews(request.getHotelReviews())
                .hotelServices(request.getHotelServices())
                .endTime(request.getEndTime())
                .photos(saved_photos_ids)
//                .room(savedRoom)
                .build();

        for(FileEntity resource : savedPhotos){
            fileService.update(resource,HOTEL_DETAILS,hotelDetails.getId());
        }


        Hotel hotel = hotelService.updateWithDetails(hotelDetails);
        hotelDetails.setHotel(hotel);
        return hotelDetailsRepository.save(hotelDetails);
    }




}
