package com.example.security.Service;

import com.example.security.Models.*;
import com.example.security.Repository.*;
import com.example.security.Request.*;
import com.example.security.RolesAndPermission.Roles;
import com.example.security.Security.Config.JwtService;
import com.example.security.Security.Token.*;
import com.example.security.Security.auth.AuthenticationResponse;

import com.example.security.Validator.ObjectsValidator;
import com.example.security.email.EmailService;
import io.netty.handler.codec.http.HttpRequest;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor

public class AuthorService {
     private final CourseRepository courseRepository;
     private final LectureRepository lectureRepository;
     private final ResourcesRepository resourcesRepository;
     private final  PasswordEncoder passwordEncoder;
     private final  AuthenticationManager authenticationManager;
    private final BaseUserRepository baseUserRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;
    private final NumberConfirmationTokenRepository numberConfRepo;
    private final ObjectsValidator<Register_Login_Request>userValidator;

    private final Validator validator;

    private static final Logger logger= LogManager.getLogger(AuthorService.class);


    public ResponseEntity<?> Create_Course(CreateCourseRequest request) {

        validator.validate(request);
        Author author= (Author) SecurityContextHolder.getContext().getAuthentication().getPrincipal();




        var Course= com.example.security.Models.Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())

                .price(request.getPrice())

                .author(author)

                .build();

        courseRepository.save(Course);
        return ResponseEntity.ok("Course Created Succsesfully");
    }

    public void Delete_Course(EditRequest request) {
        var course= courseRepository.getCourseById(request.getId());
        List<Lecture> lectures= lectureRepository.getLecturesByCourse(course);

        List<Resources> resources= resourcesRepository.getResourcesByCourseId(course.getId());

        resourcesRepository.deleteAll(resources);
        lectureRepository.deleteAll(lectures);
        courseRepository.delete(course);


    }


    public ResponseEntity<?> Create_Lecture(CreateLectureRequest request) {

        validator.validate(request);
        Course course= courseRepository.getCourseById(request.getCourse_id());



        var Lecture = com.example.security.Models.Lecture.builder()
                .name(request.getName())
                .course(course)
                .build();
        lectureRepository.save(Lecture);



        return ResponseEntity.ok("lecture created succuefully");

    }
@Async
    public void Delete_Lecture(LectureRequest request) {

        List<Lecture>lectures=lectureRepository.getLecturesByIdIn(request.getLecturers_Id());
        if(lectures.isEmpty())
            throw new IllegalStateException("NO LECTURES FOUND");
        for (Lecture lecture:lectures) {
           List< Resources> resources = resourcesRepository.findResourcesByLectureId(lecture.getId());
            if(resources!=null)
            resourcesRepository.deleteAll(resources);
        }
        lectureRepository.deleteAll(lectures);
    }

   // @Async
    public ResponseEntity<?> upload_Resource(
            MultipartFile video
            , ResourcesRequest request) throws IOException {
        if(!request.equals(null)&&!video.getBytes().equals(null)) {
            Async_Upload(video, request);
            return ResponseEntity.ok().body("UPLAODED DONE");
        }
        return ResponseEntity.badRequest().body("file not uploaded successfully : " + video.getOriginalFilename());

        /*
        Resources resources = resourcesRepository.save(Resources.builder()
                .name(video.getOriginalFilename())
                .lecture(request.getLecture())
                .size(video.getSize())
                .video(video.getBytes())
                .build());

        if (resources != null) {
            return ResponseEntity.badRequest().body("file uploaded successfully : " + video.getOriginalFilename());
        }
        return ResponseEntity.ok().body("UPLAODED DONE");*/
    }
    @Async
    protected void Async_Upload(MultipartFile video
            , ResourcesRequest request) throws IOException {
    Resources resources = resourcesRepository.save(Resources.builder()
            .name(video.getOriginalFilename())
            .lecture(request.getLecture())
                    .course(request.getCourse())
            .size(video.getSize())
            .video(video.getBytes())
            .build());
}
    public void Delete_Resource(LectureRequest request) {

        List<Resources> resources = resourcesRepository.getResourcesByIdIn(request.getResources_Id());
        if (!resources.isEmpty()) {
            resourcesRepository.deleteAll(resources);

        } else
            throw new IllegalStateException("FILE NOT FOUND TO DELETE ");
    }
//Confirm by link
/*

   public  AuthenticationResponse verifyToken(String token)
    {
        ConfirmationToken confirmationToken=confirmationRepository.findByToken(token);
       BaseUser user= baseUserRepository.findByEmailIgnoreCase(confirmationToken.getUser().getEmail());

       user.setActive(true);
    var jwtToken= jwtService.generateToken(user);


   baseUserRepository.save(user);

  saveAuthorToken(user,jwtToken);
   return AuthenticationResponse.builder()
           .token(jwtToken)
           .build();

    }

    private void saveAuthorToken(BaseUser author, String jwtToken) {
        var token = Token.builder()
                .user(author)
              //  .author(author)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
*/


}
