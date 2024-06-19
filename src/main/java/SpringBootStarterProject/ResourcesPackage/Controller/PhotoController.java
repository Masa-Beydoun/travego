package SpringBootStarterProject.ResourcesPackage.Controller;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ResourcesPackage.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final FileStorageService fileStorageService;


    @PostMapping("/upload")
    public ApiResponseClass uploadPhoto(@RequestParam("file") MultipartFile file) {
        return fileStorageService.storeFile(file,null);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<?> downloadPhoto(@PathVariable String fileName) {
        return fileStorageService.loadFileAsResource(fileName);
    }
    @GetMapping("/download/byId/{id}")
    public ResponseEntity<?> downloadPhotoById(@PathVariable Integer id) {
        return fileStorageService.loadFileAsResponseEntityById(id);
    }

}
