package com.example.security.Controller;

import com.example.security.Models.Lecture;
import com.example.security.Request.CourseRequest;
import com.example.security.Request.LectureRequest;
import com.example.security.Service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Lecture/")
@RequiredArgsConstructor
public class LectureController {

   private final LectureService lectureService;
   //DONE
    @GetMapping("Get_One_Lecture")
    public ResponseEntity<?> Get_One_Lecture(@RequestBody LectureRequest request)
    {
     Lecture lecture= lectureService.Get_One_Lecture(request);
        return ResponseEntity.ok(lecture);
    }
    //DONE
    @GetMapping("Get_All_Course_Lecture")
    public ResponseEntity<?> Get_All_Course_Lecture(@RequestBody LectureRequest request, Pageable pageable)
    {

        return ResponseEntity.ok( lectureService.Get_All_Course_Lecture(request,pageable));
    }

    //DONE
    @GetMapping("Get_Lectures_By_Name")
    public ResponseEntity<?> Get_Lectures_By_Name(@RequestBody LectureRequest request,Pageable pageable)
    {
        return ResponseEntity.ok(lectureService.Get_Lectures_By_Name(request,pageable));
    }




}
