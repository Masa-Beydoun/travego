package SpringBootStarterProject.ResourcesPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("rfe");
        try {
            FileEntity fileEntity = fileService.saveFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + fileEntity.getFilePath());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable Integer id) {
        FileEntity fileEntity = fileService.getFile(id);
        if (fileEntity != null) {
            return ResponseEntity.ok(fileEntity);
        } else {
            return ResponseEntity.status(404).body("File not found");
        }
    }
}
