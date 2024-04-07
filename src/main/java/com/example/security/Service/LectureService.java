package com.example.security.Service;
import com.example.security.Models.*;
import com.example.security.Repository.*;
import com.example.security.Request.CreateLectureRequest;
import com.example.security.Request.LectureRequest;
import com.example.security.Validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final   LectureRepository lectureRepository;

    private final CourseRepository courseRepository;
    private final ResourcesRepository resourcesRepository;

    private final ObjectsValidator<CreateLectureRequest>validator;



    public Lecture Get_One_Lecture(LectureRequest request) {
       Lecture lecture= lectureRepository.getLectureByIdAndCourse(request.getLecture_Id(),request.getCourse());

        if(lecture!=null)
            return lecture;
        throw new IllegalStateException("LECTURE NOT FOUND");
    }

    public Page<Lecture> Get_All_Course_Lecture(LectureRequest request,Pageable pageable) {

       if(pageable!=null) {

           pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").ascending());
           Page<Lecture> lectures = lectureRepository.getLecturesByCourse(request.getCourse(), pageable);
           return lectures;
       }
        else

        throw new IllegalStateException("NO LECTURERS FOUND");
    }

    public Page<Lecture> Get_Lectures_By_Name(LectureRequest request,Pageable pageable) {

      Page<Lecture> lectures= lectureRepository.getLecturesByNameLike("%"+request.getName()+"%",pageable);
        if(!lectures.isEmpty())
            return lectures;
        throw new IllegalStateException("NO LECTURE NAMED LIKE "+request.getName()+" FOUND");


    }


//USING JPA REPOSITORY
    /*
    public  ResponseEntity<?> Get_Lecture(LectureRequest request)
    {
      return   lectureRepository.getLectureByCourse(request.getCourse());

    }


    public  ResponseEntity<?> Get_Lecture_By_Name(LectureRequest request)
    {
        return   lectureRepository.findByNameLike(request.getName());

    }

    public  ResponseEntity<?> Get_All_Lecture(LectureRequest request)
    {
        return   lectureRepository.getLecturesByCourse(request.getCourse());

    }

*/

}
