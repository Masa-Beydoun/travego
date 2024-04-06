package com.example.security.Controller;

import com.example.security.Models.BaseUser;
import com.example.security.Repository.BaseUserRepository;
import com.example.security.Request.*;
import com.example.security.Security.Token.ConfirmationRepository;
import com.example.security.Security.Token.ConfirmationToken;
import com.example.security.Security.auth.AuthenticationResponse;
import com.example.security.Service.AuthorService;
import com.example.security.Service.CourseService;
import com.example.security.Service.LectureService;
import com.example.security.Service.ResourcesService;
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Author")

public class AuthorController {
   private final AuthorService authorService;
    private final CourseService courseService;
    private final  LectureService lectureService;
    private final ConfirmationToken confirmationToken;
    private final ConfirmationRepository confirmationRepository;
    private final  ResourcesService resourcesService;
    private final BaseUserRepository baseUserRepository;



    @GetMapping
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String token) {
      var genratedToken= authorService.verifyToken(token);

     return   ResponseEntity.ok().header(genratedToken.getToken()).body(genratedToken.getToken());
    }

    //DONE
    @PostMapping("/Create_Course")
    public ResponseEntity<?> Create_Course(@RequestBody CreateCourseRequest request)
    {
        courseService.Create_Course(request);
        return ResponseEntity.ok().body("Course created successfully");
    }
    //DONE
    @DeleteMapping("/Delete_Course")
    public ResponseEntity<?> Delete_Course(@RequestBody EditRequest request)
    {
        courseService.Delete_Course(request);
        return ResponseEntity.ok().body("Course deleted succsessfully");
    }

    //DONE
    @PostMapping("/Create_Lecture")
    public ResponseEntity<?> Create_Lecture(@RequestBody CreateLectureRequest request)
    {lectureService.Create_Lecture(request);
        return ResponseEntity.ok().body("Lecture created succsessfully");
    }
    //DONE
    @DeleteMapping("/Delete_Lecture")
    public ResponseEntity<?> Delete_Lecture(@RequestBody LectureRequest request)
    {
        lectureService.Delete_Lecture(request);
        return ResponseEntity.ok().body("LECTURERS Deleted Succsessfully");
    }

    @PostMapping("/upload_vid")
    public ResponseEntity<?> upload_vid(@RequestPart("video")MultipartFile video,@RequestPart("request") ResourcesRequest request) throws IOException {
        resourcesService.upload_vid(video,request);
        return ResponseEntity.ok().body("Lecture created succsessfully");
    }


}
