package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.CommentPackage.Models.Comment;
import SpringBootStarterProject.HotelsPackage.Models.*;

import SpringBootStarterProject.HotelsPackage.Repository.HotelDetailsRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelRepository;
import SpringBootStarterProject.HotelsPackage.Repository.HotelServicesRepository;
import SpringBootStarterProject.HotelsPackage.Repository.RoomRepository;
import SpringBootStarterProject.HotelsPackage.Request.HotelDetailsRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelDetailsResponse;
import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.HotelsPackage.Response.HotelServicesResponse;
import SpringBootStarterProject.HotelsPackage.Response.RoomResponse;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ResourcesPackage.Enum.ResourceType;
import SpringBootStarterProject.ResourcesPackage.Model.FileMetaData;
import SpringBootStarterProject.ResourcesPackage.Repository.FileMetaDataRepository;
import SpringBootStarterProject.ResourcesPackage.Response.FileMetaDataResponse;
import SpringBootStarterProject.ResourcesPackage.service.FileStorageService;
import SpringBootStarterProject.ReviewsPackage.Models.HotelReview;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static SpringBootStarterProject.ResourcesPackage.Enum.ResourceType.HOTEL_DETAILS;

@Service
@RequiredArgsConstructor
public class HotelDetailsService {

    private final HotelDetailsRepository hotelDetailsRepository;
    private final HotelService hotelService;
    private final HotelServicesRepository hotelServicesRepository;
    private final FileMetaDataRepository fileMetaDataRepository;
    private final FileStorageService fileStorageService;
    private final RoomRepository roomRepository;

    private final ObjectsValidator<HotelDetailsRequest> validator;
    private final HotelRepository hotelRepository;
    private final RoomService roomService;

    public ApiResponseClass getHotelDetailsByHotelId(Integer id){

        HotelDetails details =  hotelDetailsRepository.findByHotelId(id).orElseThrow(()-> new RequestNotValidException("Hotel Details not found"));
        return getHotelDetailsById(details.getId());
    }


    public ApiResponseClass save(HotelDetailsRequest request) {

        validator.validate(request);

        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(()-> new RequestNotValidException("Hotel not found"));


        HotelDetails h= hotelDetailsRepository.findByHotelId(hotel.getId()).orElse(null);

        if(h!= null) throw new RequestNotValidException("Hotel-Details already exists");

        // from request
        List<MultipartFile> requestedPhotos = request.getPhotos();
        //to save in hotel details
        List<Integer> saved_photos_ids = new ArrayList<>();

        for (MultipartFile resource : requestedPhotos) {
            FileMetaData savedPhoto =fileStorageService.storeFileOtherEntity(resource, HOTEL_DETAILS);
            saved_photos_ids.add(savedPhoto.getId());
        }
//check services
        List<Room> savedRoom = new ArrayList<>();
        List<String> requestServices = request.getHotelServices();
        List<HotelServices> services = new ArrayList<>();
        for (String name : requestServices) {
            HotelServices ser = hotelServicesRepository.findByName(name).orElseThrow(() -> new RequestNotValidException("Hotel Service not found"));
            services.add(ser);
        }
//no reviews and comment yet
        List<HotelReview> hotelReview = new ArrayList<>();
        List<Comment> commentReview = new ArrayList<>();

        HotelDetails hotelDetails = HotelDetails.builder()
                .breakfastPrice(request.getBreakfastPrice())
                .distanceFromCity(request.getDistanceFromCity())
                .priceForExtraBed(request.getPriceForExtraBed())
                .startTime(request.getStartTime())
                .commentReviews(commentReview)
                .reviews(hotelReview)
                .hotel(hotel)
                .hotelServices(services)
                .endTime(request.getEndTime())
                .location(request.getLocation())
                .cleanliness(request.getCleanliness())
                .facilities(request.getFacilities())
                .security(request.getSecurity())
                .numOfReviews(1)
                .averageRating((request.getLocation()+request.getCleanliness()+request.getFacilities()+request.getSecurity())/4.0)
                .photos(saved_photos_ids)
                .room(savedRoom)
                .build();
        hotelDetailsRepository.save(hotelDetails);

        List<FileMetaDataResponse> photoResponse=new ArrayList<>();
        for(Integer id : saved_photos_ids){
            FileMetaData saved = fileMetaDataRepository.findById(id).orElseThrow(()->new RequestNotValidException("Photos not found"));
            saved.setRelationId(hotelDetails.getId());
            fileMetaDataRepository.save(saved);
            FileMetaDataResponse r1=FileMetaDataResponse.builder()
                    .id(saved.getId())
                    .fileName(saved.getFileName())
                    .fileSize(saved.getFileSize())
                    .fileType(saved.getFileType())
                    .filePath(saved.getFilePath())
                    .relationId(saved.getRelationId())
                    .relationType(saved.getRelationType())
                    .build();
            photoResponse.add(r1);
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

        HotelResponse hotelResponse = hotelService.getHotelResponse(hotel);
        List<RoomResponse> roomResponses = new ArrayList<>();
        List<Room> rooms = hotelDetails.getRoom();
        for(Room room : rooms) {
            RoomResponse response = RoomResponse.builder()
                    .id(room.getId())
                    .type(room.getType().name())
                    .totalNumberOfRooms(room.getTotalNumberOfRooms())
                    .roomServices(room.getRoomServices())
                    .maxNumOfPeople(room.getMaxNumOfPeople())
                    .num_of_bed(room.getNum_of_bed())
                    .price(room.getPrice())
                    .space(room.getSpace())
                    .build();
            roomResponses.add(response);
        }


        HotelDetailsResponse response = HotelDetailsResponse.builder()
                    .id(hotelDetails.getId())
                    .breakfastPrice(hotelDetails.getBreakfastPrice())
                    .distanceFromCity(hotelDetails.getDistanceFromCity())
                    .priceForExtraBed(hotelDetails.getPriceForExtraBed())
                    .startTime(hotelDetails.getStartTime())
                    .commentReviews(hotelDetails.getCommentReviews())
                    .hotelServices(servicesResponse)
                    .endTime(hotelDetails.getEndTime())
                    .hotel(hotelResponse)
                    .hotel(hotelResponse)
                    .room(roomResponses)
                    .photo(photoResponse)
                    .facilities(hotelDetails.getFacilities())
                    .cleanliness(hotelDetails.getCleanliness())
                    .location(hotelDetails.getLocation())
                    .security(hotelDetails.getSecurity())
                    .averageRating(hotelDetails.getAverageRating())
                    .build();

        return new ApiResponseClass("Hotel-Details saved successfully",HttpStatus.CREATED,LocalDateTime.now(),response);
    }


    public ApiResponseClass delete(Integer id) {

        HotelDetails hotelDetails = hotelDetailsRepository.findById(id).orElseThrow(()->new RequestNotValidException("Hotel Details not found"));

        for (HotelServices hotelServices : hotelDetails.getHotelServices()) {
            hotelServices.getDetails().remove(hotelDetails);
            hotelServicesRepository.save(hotelServices);
        }

        hotelDetails.getHotelServices().clear();

        hotelDetailsRepository.delete(hotelDetails);

        return new ApiResponseClass("Deleted successfully",HttpStatus.OK,LocalDateTime.now());
    }

    public ApiResponseClass getHotelDetailsById(Integer id) {

        HotelDetails details = hotelDetailsRepository.findById(id).orElseThrow(()-> new RequestNotValidException("Hotel Details not found"));



        List<Room> rooms = roomRepository.findAllByHotelDetailsId(details.getId()).orElseThrow(()->new RequestNotValidException("Rooms not found"));


        List<FileMetaData> photos = fileMetaDataRepository.findAllByRelationTypeAndRelationId(ResourceType.valueOf(HOTEL_DETAILS.name()),id).orElseThrow(()->new RequestNotValidException("Photos not found"));
        List<FileMetaDataResponse> photoResponse=new ArrayList<>();
        for(FileMetaData saved : photos){
            FileMetaDataResponse r1=FileMetaDataResponse.builder()
                    .id(saved.getId())
                    .fileName(saved.getFileName())
                    .fileSize(saved.getFileSize())
                    .fileType(saved.getFileType())
                    .filePath(saved.getFilePath())
                    .relationId(saved.getRelationId())
                    .relationType(saved.getRelationType())
                    .build();
            photoResponse.add(r1);
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
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(Room room : rooms) {
            RoomResponse response = RoomResponse.builder()
                    .id(room.getId())
                    .type(room.getType().name())
                    .totalNumberOfRooms(room.getTotalNumberOfRooms())
                    .roomServices(room.getRoomServices())
                    .maxNumOfPeople(room.getMaxNumOfPeople())
                    .num_of_bed(room.getNum_of_bed())
                    .price(room.getPrice())
                    .space(room.getSpace())
                    .build();
            roomResponses.add(response);
        }


        Hotel hotel = details.getHotel();
        HotelResponse hotelResponse = hotelService.getHotelResponse(hotel);


        HotelDetailsResponse response =  HotelDetailsResponse.builder()
                .id(details.getId())
                .breakfastPrice(details.getBreakfastPrice())
                .distanceFromCity(details.getDistanceFromCity())
                .priceForExtraBed(details.getPriceForExtraBed())
                .startTime(details.getStartTime())
                .commentReviews(details.getCommentReviews())
                .hotelServices(servicesResponse)
                .endTime(details.getEndTime())
                .hotel(hotelResponse)
                .room(roomResponses)
                .photo(photoResponse)
                .security(details.getSecurity())
                .cleanliness(details.getCleanliness())
                .location(details.getLocation())
                .facilities(details.getFacilities())
                .averageRating(details.getAverageRating())
                .build();
        return new ApiResponseClass("Hotel-Details found",HttpStatus.OK,LocalDateTime.now(),response);

    }
}
