package SpringBootStarterProject.ResourcesPackage.Controller;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ResourcesPackage.service.FileStorageService;
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
@RequestMapping("/")
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

}
