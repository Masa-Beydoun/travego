package SpringBootStarterProject.ResourcesPackage;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final FileStorageService fileStorageService;


    @PostMapping("/upload")
    public ApiResponseClass uploadPhoto(@RequestParam("file") MultipartFile file) {
        return fileStorageService.storeFile(file,null,null);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<?> downloadPhoto(@PathVariable String fileName) {
        return fileStorageService.loadFileAsResource(fileName);
    }
    @GetMapping("/download/byId/{id}")
    public ResponseEntity<?> downloadPhotoById(@PathVariable Integer id) {
        return fileStorageService.loadFileAsResourceById(id);
    }

}
