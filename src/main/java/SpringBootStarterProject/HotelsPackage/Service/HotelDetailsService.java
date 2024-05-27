package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Models.HotelServices;
import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelServicesRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelDetailsRequest;
import SpringBootStarterProject.HotelsPackage.Request.HotelRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelDetailsResponse;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.HotelsPackage.Response.HotelServicesResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ResourcesPackage.FileEntity;
import SpringBootStarterProject.ResourcesPackage.FileMetaDataRepository;
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
    private final HotelServicesRepository hotelServicesRepository;
    private final FileMetaDataRepository fileMetaDataRepository;
    private final FileService fileService;
//    private final RoomService roomService;
    private final HotelCommentReviewService hotelCommentReviewService;

    private final ObjectsValidator<HotelDetailsRequest> validator;
    private final HotelRepository hotelRepository;

    public HotelDetailsResponse getHotelDetailsById(Integer id){

        HotelDetails details =  hotelDetailsRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel Details not found"));

        List<Integer> requestedPhotosIds =details.getPhotos();
        List<FileEntity> photos = new ArrayList<>();
        for(Integer photoId : requestedPhotosIds){
            FileEntity photo=fileMetaDataRepository.findById(photoId).orElseThrow(()-> new RequestNotValidException("Photo not found"));
            photos.add(photo);
        }

        List<HotelServicesResponse> servicesResponse = new ArrayList<>();
        List<HotelServices> servicesRequested = details.getHotelServices();
        for(HotelServices service : servicesRequested) {
            servicesResponse.add(
                    HotelServicesResponse.builder()
                            .id(service.getId())
                            .name(service.getName())
                            .build()
                    );
        }
        return HotelDetailsResponse.builder()
                .id(details.getId())
                .breakfastPrice(details.getBreakfastPrice())
                .distanceFromCity(details.getDistanceFromCity())
                .priceForExtraBed(details.getPriceForExtraBed())
                .startTime(details.getStartTime())
                .commentReviews(details.getCommentReviews())
                .reviews(details.getReviews())
                .hotelServices(servicesResponse)
                .endTime(details.getEndTime())
                .hotel(details.getHotel())
                .room(details.getRoom())
                .photo(photos)
                .build();
    }

    public HotelDetailsResponse save(HotelDetailsRequest request) {

        validator.validate(request);

        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(()-> new RequestNotValidException("Hotel not found"));


        HotelDetails h= hotelDetailsRepository.findByHotelId(hotel.getId());
        if(h!= null) throw new RequestNotValidException("Hotel-Details already exists");


        List<FileEntity> savedPhotos = new ArrayList<>();
        List<MultipartFile> requestedPhotos = request.getPhotos();
        List<Integer> saved_photos_ids = new ArrayList<>();
        for (MultipartFile resource : requestedPhotos) {
            FileEntity savedPhoto = fileService.saveFile(resource);
            savedPhotos.add(savedPhoto);
            saved_photos_ids.add(savedPhoto.getId());
        }

            //TODO : room service and saving it in the hotelDetails
//        Room savedRoom = roomService.save(request.getRoom());

            List<String> requestServices = request.getHotelServices();
            List<HotelServices> services = new ArrayList<>();
            for (String name : requestServices) {
                HotelServices ser = hotelServicesRepository.findByName(name).orElseThrow(() -> new RequestNotValidException("Hotel Service not found"));
                services.add(ser);
            }

            HotelDetails hotelDetails = HotelDetails.builder()
                    .breakfastPrice(request.getBreakfastPrice())
                    .distanceFromCity(request.getDistanceFromCity())
                    .priceForExtraBed(request.getPriceForExtraBed())
                    .startTime(request.getStartTime())
                    .commentReviews(null)
                    .reviews(null)
                    .hotel(hotel)
                    .hotelServices(services)
                    .endTime(request.getEndTime())
                    .photos(saved_photos_ids)
//                .room(savedRoom)
                    .build();
        HotelDetails savedDetails = hotelDetailsRepository.save(hotelDetails);

        List<FileEntity> savedPhotos2 = new ArrayList<>();
        for (FileEntity file : savedPhotos) {
            savedPhotos2.add(fileService.update(file, HOTEL_DETAILS, hotelDetails.getId()));
        }

        List<HotelServicesResponse> servicesResponse = new ArrayList<>();
        List<HotelServices> hotelServices = hotelDetails.getHotelServices();
        for(HotelServices service : hotelServices) {
            servicesResponse.add(
                    HotelServicesResponse.builder()
                            .id(service.getId())
                            .name(service.getName())
                            .build()
            );
        }

        return HotelDetailsResponse.builder()
                    .id(savedDetails.getId())
                    .breakfastPrice(savedDetails.getBreakfastPrice())
                    .distanceFromCity(savedDetails.getDistanceFromCity())
                    .priceForExtraBed(savedDetails.getPriceForExtraBed())
                    .startTime(savedDetails.getStartTime())
                    .commentReviews(savedDetails.getCommentReviews())
                    .reviews(savedDetails.getReviews())
                    .hotelServices(servicesResponse)
                    .endTime(savedDetails.getEndTime())
                    .hotel(hotel)
                    .room(savedDetails.getRoom())
                    .photo(savedPhotos2)
                    .build();


    }


    public void delete(Integer id) {
        hotelDetailsRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel Details not found"));
        hotelDetailsRepository.deleteById(id);
    }

}
