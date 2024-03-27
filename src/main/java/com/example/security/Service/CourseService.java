package com.example.security.Service;

import com.example.security.Caching.CacheService;
import com.example.security.Models.Author;
import com.example.security.Models.Course;
import com.example.security.Models.Lecture;
import com.example.security.Models.Resources;
import com.example.security.Repository.AuthorRepository;
import com.example.security.Repository.CourseRepository;
import com.example.security.Repository.LectureRepository;
import com.example.security.Repository.ResourcesRepository;
import com.example.security.Request.CourseRequest;
import com.example.security.Request.CreateCourseRequest;
import com.example.security.Request.EditRequest;
import com.example.security.Request.GetCourseRequest;
import com.example.security.RolesAndPermission.Roles;
import com.example.security.Specification.CourseSpecification;
import com.example.security.Validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final   CourseRepository courseRepository;
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResourcesRepository resourcesRepository;
    private final LectureRepository lectureRepository;
  private final CacheService cacheService;

  private final ObjectsValidator<CreateCourseRequest>validator;
    /*
       public Object Create_Course(CourseRequest request) {
          return  (Author)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       }
      */
  //  @Cacheable("students")
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


    public Page<Course> Get_Course_By_Name(CourseRequest request,Pageable pageable) {


        Page<Course> courses= courseRepository.findCourseByTitleLike("%"+request.getTitle()+"%",pageable);
        if(courses.isEmpty())
            throw new IllegalStateException("COURSE NOT FOUND");

       return courses;

    }


    public GetCourseRequest Get_Course_By_Id(CourseRequest request) {
       Course course = courseRepository.getCourseById(request.getId());

       if(!course.equals(null))
       {
           GetCourseRequest the_course=  new GetCourseRequest(
                    course.getId()
                   ,course.getTitle()
                   ,course.getDescription()
                   ,course.getPrice()
                   ,course.getAuthor().getName());
           return the_course;
       }
       throw new IllegalStateException("COURSE NOT FOUND");

    }
   // @CachePut("students")

    //PAGINATIONG
    public Page<GetCourseRequest> getAllCourses(Pageable pageable)
    {
     Page <Course> course=courseRepository.findAll(pageable);
     List<GetCourseRequest> getCourseRequests = new ArrayList<>();

      for (Course course1:course) {
          GetCourseRequest the_course = new GetCourseRequest(
                  course1.getId(),
                  course1.getTitle(),
                  course1.getDescription(),
                  course1.getPrice(),
                  course1.getAuthor().getName()
          );

          getCourseRequests.add(the_course);

      }
        Page<GetCourseRequest> page = new PageImpl<>(getCourseRequests, pageable, course.getTotalElements());
      cacheService.printCacheContents();
      return page;
    }

    public void Delete_Course(EditRequest request) {
         var course= courseRepository.getCourseById(request.getId());
        List<Lecture> lectures= lectureRepository.getLecturesByCourse(course);

        Resources resources= resourcesRepository.getResourcesByLectureId(lectures.get(0).getId());

        resourcesRepository.delete(resources);
        lectureRepository.deleteAll(lectures);
        courseRepository.delete(course);


    }
}
