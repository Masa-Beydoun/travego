package com.example.security.Controller;

import com.example.security.Models.Course;
import com.example.security.Repository.CourseRepository;
import com.example.security.Request.CourseRequest;
import com.example.security.Request.TestRequest;
import com.example.security.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Course/")
@RequiredArgsConstructor
public class CourseController {
private final CourseService courseService;

//USING SPECICFICATION ?

    //DONE
   @GetMapping("Get_Course_By_Name")
    public ResponseEntity<?> Get_Course(@RequestBody CourseRequest request,Pageable pageable)
    {
        Page<Course> courses=courseService.Get_Course_By_Name(request,pageable );
        return ResponseEntity.ok(courses);
    }

    //DONE
    @GetMapping("Get_Course_By_Id")
    public ResponseEntity<?> Get_Course_By_Id(@RequestBody CourseRequest request)
    {
        return ResponseEntity.ok(courseService.Get_Course_By_Id(request));
    }
    //DONE
    @GetMapping("get_All_Courses")
    public ResponseEntity<?> getAllCourses( Pageable request)
    {
        return ResponseEntity.ok(courseService.getAllCourses(request));
    }



}
