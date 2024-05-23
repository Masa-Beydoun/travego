package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelServicesRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelDetailsRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelDetailsResponse;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.HotelsPackage.Response.HotelServicesResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ResourcesPackage.FileService;
import SpringBootStarterProject.Trip_package.Models.HotelServices;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelDetailsService {

    private final HotelDetailsRepository hotelDetailsRepository;
    private final HotelService hotelService;
    private final HotelServicesRepository hotelServicesRepository;
    private final FileService fileService;
//    private final RoomService roomService;
    private final HotelCommentReviewService hotelCommentReviewService;

    private final ObjectsValidator<HotelDetailsRequest> validator;

    public HotelDetailsResponse getHotelDetailsById(Integer id){

        HotelDetails details =  hotelDetailsRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel Details not found"));



        return HotelDetailsResponse.builder()
                .id(details.getId())
                .breakfastPrice(details.getBreakfastPrice())
                .distanceFromCity(details.getDistanceFromCity())
                .priceForExtraBed(details.getPriceForExtraBed())
                .startTime(details.getStartTime())
                .commentReviews(details.getCommentReviews())
                .reviews(details.getReviews())
                .hotelServices(details.getHotelServices())
                .endTime(details.getEndTime())
                .hotel(details.getHotel())
                .room(details.getRoom())
                .build();
    }

    public HotelDetailsResponse save(HotelDetailsRequest request) {

        validator.validate(request);

        HotelResponse hotelResponse = hotelService.findHotelById(request.getHotelId());

//        List<FileEntity> savedPhotos = new ArrayList<>();
//        List<MultipartFile> requestedPhotos = request.getPhotos();
//        List<Integer> saved_photos_ids = new ArrayList<>();
//        for(MultipartFile resource : requestedPhotos){
//            FileEntity savedPhoto = fileService.saveFile(resource);
//            savedPhotos.add(savedPhoto);
//            saved_photos_ids.add(savedPhoto.getId());
//        }

        //TODO : room service and saving it in the hotelDetails
//        Room savedRoom = roomService.save(request.getRoom());

        List<String> requestServices= request.getHotelServices();
        List<HotelServices> services = new ArrayList<>();
        for(String name: requestServices){
            HotelServices ser = hotelServicesRepository.findByName(name).orElseThrow(()-> new RequestNotValidException("Hotel Service not found"));
            services.add(ser);
        }

        HotelDetails hotelDetails = HotelDetails.builder()
                .breakfastPrice(request.getBreakfastPrice())
                .distanceFromCity(request.getDistanceFromCity())
                .priceForExtraBed(request.getPriceForExtraBed())
                .startTime(request.getStartTime())
                .commentReviews(null)
                .reviews(null)
                .hotelServices(services)
                .endTime(request.getEndTime())
//                .photos(saved_photos_ids)
//                .room(savedRoom)
                .build();

//        for(FileEntity resource : savedPhotos){
//            fileService.update(resource,HOTEL_DETAILS,hotelDetails.getId());
//        }


        Hotel hotel = hotelService.updateWithDetails(hotelDetails);
        hotelDetails.setHotel(hotel);
        HotelDetails details = hotelDetailsRepository.save(hotelDetails);
        return HotelDetailsResponse.builder()
                .id(details.getId())
                .breakfastPrice(details.getBreakfastPrice())
                .distanceFromCity(details.getDistanceFromCity())
                .priceForExtraBed(details.getPriceForExtraBed())
                .startTime(details.getStartTime())
                .commentReviews(details.getCommentReviews())
                .reviews(details.getReviews())
                .hotelServices(details.getHotelServices())
                .endTime(details.getEndTime())
                .hotel(details.getHotel())
                .room(details.getRoom())
                .build();

    }




}
