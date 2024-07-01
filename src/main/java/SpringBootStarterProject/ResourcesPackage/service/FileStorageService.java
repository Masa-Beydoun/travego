package SpringBootStarterProject.ResourcesPackage.service;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ResourcesPackage.Enum.ResourceType;
import SpringBootStarterProject.ResourcesPackage.Model.FileMetaData;
import SpringBootStarterProject.ResourcesPackage.Repository.FileMetaDataRepository;
import SpringBootStarterProject.ResourcesPackage.Response.FileMetaDataResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final FileMetaDataRepository fileMetaDataRepository;



    public FileStorageService(@Value("${file.upload-dir}") String uploadDir, FileMetaDataRepository fileMetaDataRepository) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.fileMetaDataRepository = fileMetaDataRepository;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public ApiResponseClass storeFile(MultipartFile file, ResourceType type) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            FileMetaData meta = FileMetaData.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .relationType(type)
                    .build();
            fileMetaDataRepository.save(meta);
            meta.setFilePath("travego.onrender.com/uploads/" + meta.getId());
            fileMetaDataRepository.save(meta);

            FileMetaDataResponse response = FileMetaDataResponse.builder()
                    .id(meta.getId())
                    .filePath(meta.getFilePath())
                    .fileName(meta.getFileName())
                    .fileType(meta.getFileType())
                    .fileSize(meta.getFileSize())
                    .relationId(meta.getRelationId())
                    .relationType(meta.getRelationType())
                    .build();
            return new ApiResponseClass("Photo uploaded successfully", HttpStatus.OK, LocalDateTime.now(),response);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public FileMetaData storeFileOtherEntity(MultipartFile file, ResourceType type) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Create metadata
            FileMetaData meta = FileMetaData.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .relationType(type)
                    .build();
            fileMetaDataRepository.save(meta);
            meta.setFilePath("travego.onrender.com/uploads/" + meta.getId());
            fileMetaDataRepository.save(meta);
            // Prepare metadata response
            return  FileMetaData.builder()
                    .id(meta.getId())
                    .fileName(meta.getFileName())
                    .fileType(meta.getFileType())
                    .fileSize(meta.getFileSize())
                    .relationId(meta.getRelationId())
                    .relationType(meta.getRelationType())
                    .build();

            // Encode file data as Base64
//            byte[] fileBytes = IOUtils.toByteArray(Files.newInputStream(targetLocation));
//            String base64File = Base64.getEncoder().encodeToString(fileBytes);

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public ResponseEntity<?> loadFileAsResource(String fileName) {

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();;
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = "application/octet-stream";
                try {
                    contentType = filePath.toUri().toURL().openConnection().getContentType();
                } catch (IOException ex) {
                    System.out.println("Could not determine file type.");
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not find file: " + fileName);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
    }

    public ResponseEntity<?> loadFileAsResponseEntityById(Integer id) {

        try {
            FileMetaData metaData = fileMetaDataRepository.findById(id).orElseThrow(()->new RequestNotValidException("Photo not found"));
            Path filePath = this.fileStorageLocation.resolve(metaData.getFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = "application/octet-stream";
                try {
                    contentType = filePath.toUri().toURL().openConnection().getContentType();
                } catch (IOException ex) {
                    System.out.println("Could not determine file type.");
                }

                FileMetaDataResponse response = FileMetaDataResponse.builder().build();
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not find file: " + metaData.getFileName());
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
    }

    public Resource loadFileAsResourceById(Integer id) {

        try {
            FileMetaData metaData = fileMetaDataRepository.findById(id).orElseThrow(()->new RequestNotValidException("Photo not found"));
            Path filePath = this.fileStorageLocation.resolve(metaData.getFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not find file: " + metaData.getFileName());
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
    }

    public FileMetaDataResponse loadFileAsFileMetaDataById(Integer id) {

        try {
            FileMetaData metaData = fileMetaDataRepository.findById(id).orElseThrow(() -> new RequestNotValidException("Photo not found"));
            Path filePath = this.fileStorageLocation.resolve(metaData.getFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return FileMetaDataResponse.builder()
                        .id(metaData.getId())
                        .fileName(metaData.getFileName())
                        .fileType(metaData.getFileType())
                        .fileSize(metaData.getFileSize())
                        .filePath(metaData.getFilePath())
                        .relationId(metaData.getRelationId())
                        .relationType(metaData.getRelationType())
                        .build();
            } else {
                throw new RequestNotValidException("Could not find file: " + metaData.getFileName());
            }
        } catch (IOException ex) {
            throw new RequestNotValidException("Error: " + ex.getMessage());
        }
    }


}
