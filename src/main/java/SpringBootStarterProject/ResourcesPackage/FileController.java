package SpringBootStarterProject.ResourcesPackage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            description = "This endpoint save a resource in our system",
            summary = "Get All hotels by city id",
            responses = {
                    @ApiResponse(
                            description = "saved successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileEntity fileEntity = fileService.saveFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + fileEntity.getFilePath());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(
            description = "This endpoint get a resource by its idin our system",
            summary = "Get One resource by its id",
            responses = {
                    @ApiResponse(
                            description = "uploaded successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getFile(@PathVariable Integer id) {
        FileEntity fileEntity = fileService.getFile(id);
        if (fileEntity != null) {
            return ResponseEntity.ok(fileEntity);
        } else {
            return ResponseEntity.status(404).body("File not found");
        }
    }
}
