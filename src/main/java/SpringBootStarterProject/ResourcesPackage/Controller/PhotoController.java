package SpringBootStarterProject.ResourcesPackage.Controller;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ResourcesPackage.Request.FileInformationRequest;
import SpringBootStarterProject.ResourcesPackage.service.FileStorageService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final FileStorageService fileStorageService;


    @PostMapping(name = "/upload",consumes = "multipart/form-data")
    public ApiResponseClass uploadPhoto(@RequestParam("file") MultipartFile file) {
        return fileStorageService.storeFile(file,null);
    }

//    @GetMapping("/download/{fileName:.+}")
//    public ResponseEntity<?> downloadPhoto(@PathVariable String fileName) {
//        return fileStorageService.loadFileAsResource(fileName);
//    }
    @GetMapping("/uploads/{id}")
    public ResponseEntity<?> downloadPhotoById(@PathVariable Integer id) {
        return fileStorageService.loadFileAsResponseEntityById(id);
    }



    @PutMapping("{id}")
    public ApiResponseClass updatePhoto(@PathVariable Integer id,@RequestBody FileInformationRequest request) {
        return fileStorageService.updateFile(id,request);
    }
    @GetMapping("all")
    public ApiResponseClass allPhotos() {
        return  fileStorageService.getAllPhotos();
    }


    @PutMapping
    @Hidden
    public ApiResponseClass updatePhotos() {
        return fileStorageService.updateAllFiles();
    }
}
