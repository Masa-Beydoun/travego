package SpringBootStarterProject.ResourcesPackage.service;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.exception.RequestNotValidException;
import SpringBootStarterProject.ResourcesPackage.Enum.ResourceType;
import SpringBootStarterProject.ResourcesPackage.Model.FileMetaData;
import SpringBootStarterProject.ResourcesPackage.Repository.FileMetaDataRepository;
import SpringBootStarterProject.ResourcesPackage.Request.FileInformationRequest;
import SpringBootStarterProject.ResourcesPackage.Response.FileMetaDataResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final FileMetaDataRepository fileMetaDataRepository;
    private final ResourceLoader resourceLoader;


    public FileStorageService(@Value("${file.upload-dir}") String uploadDir, FileMetaDataRepository fileMetaDataRepository,ResourceLoader resourceLoader) {
        this.resourceLoader=resourceLoader;
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.fileMetaDataRepository = fileMetaDataRepository;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public ApiResponseClass storeFile(MultipartFile file, ResourceType type) {
            FileMetaData  meta = storeFileOtherEntity(file, type);
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
    }

    public FileMetaData storeFileOtherEntity(MultipartFile file, ResourceType type) {
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
            meta.setFilePath("travego.onrender.com/uploads/" + meta.getId());
            fileMetaDataRepository.save(meta);

            return  FileMetaData.builder()
                    .id(meta.getId())
                    .fileName(meta.getFileName())
                    .fileType(meta.getFileType())
                    .fileSize(meta.getFileSize())
                    .relationId(meta.getRelationId())
                    .relationType(meta.getRelationType())
                    .build();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
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

    public FileMetaDataResponse loadFileAsFileMetaDataById(Integer id) {
        FileMetaData metaData = fileMetaDataRepository.findById(id).orElseThrow(()->new RequestNotValidException("Photo not found"));
        return FileMetaDataResponse.builder()
                .id(metaData.getId())
                .fileName(metaData.getFileName())
                .fileType(metaData.getFileType())
                .fileSize(metaData.getFileSize())
                .filePath(metaData.getFilePath())
                .relationId(metaData.getRelationId())
                .relationType(metaData.getRelationType())
                    .build();

    }


    public ApiResponseClass getAllPhotos() {
        List<FileMetaData> files = fileMetaDataRepository.findAll();
        List<FileMetaDataResponse> responses=new ArrayList<>();
        for (FileMetaData file : files) {
            FileMetaDataResponse response = FileMetaDataResponse.builder()
                    .id(file.getId())
                    .fileName(file.getFileName())
                    .fileType(file.getFileType())
                    .fileSize(file.getFileSize())
                    .filePath(file.getFilePath())
                    .relationId(file.getRelationId())
                    .relationType(file.getRelationType())
                    .build();
            responses.add(response);
        }
        return new ApiResponseClass("All photos got successfully", HttpStatus.OK, LocalDateTime.now(),responses);
    }

    public ApiResponseClass updateFile(Integer id,FileInformationRequest request) {
        FileMetaData file = fileMetaDataRepository.findById(id).orElseThrow(()->new RequestNotValidException("Photo not found"));
        file.setRelationId(request.getRelationId());
        file.setRelationType(ResourceType.valueOf(request.getRelationType()));
        fileMetaDataRepository.save(file);
        FileMetaDataResponse response = FileMetaDataResponse.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .fileType(file.getFileType())
                .fileSize(file.getFileSize())
                .filePath(file.getFilePath())
                .relationId(file.getRelationId())
                .relationType(file.getRelationType())
                .build();
        return new ApiResponseClass("File updated successfully", HttpStatus.OK, LocalDateTime.now(),response);

    }
}
