package SpringBootStarterProject.ResourcesPackage;

import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
//import com.example.fileupload.model.Hotel;
//import com.example.fileupload.repository.FileMetaDataRepository;
//import com.example.fileupload.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class FileService {

    private final String uploadDir = "uploads/";


    private final FileMetaDataRepository fileMetaDataRepository;



    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!");
        }
    }

    public FileEntity saveFile(MultipartFile file) {
        try {
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, file.getBytes());

            String fileName = file.getOriginalFilename();

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setFilePath(path.toString());
            fileEntity.setFileType(file.getContentType());
            fileEntity.setFileSize(file.getSize());
            fileEntity.setFileType(file.getContentType());

            return fileMetaDataRepository.save(fileEntity);
        } catch (IOException e) {
            throw new RuntimeException("Could not save file. Error: " + e.getMessage());
        }
    }

    public FileEntity getFile(Integer id) {
        return fileMetaDataRepository.findById(id).orElseThrow(()-> new RequestNotValidException("File not found"));
    }


//    public byte[] getFile(Integer fileId) {
//        try {
//            FileMetaData fileMetaData = fileMetaDataRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
//            Path path = Paths.get(fileMetaData.getFilePath());
//            return Files.readAllBytes(path);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not read file. Error: " + e.getMessage());
//        }
//    }

    public FileEntity update(FileEntity fileEntity, ResourceType resourceType, Integer relation_id){
        FileEntity file = fileMetaDataRepository.findById(fileEntity.getId()).orElseThrow(() -> new RequestNotValidException("File not found"));
        file.setRelationType(resourceType);
        file.setRelationId(relation_id);
        return file;
    }

}
