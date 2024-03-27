package com.example.security.Repository;

import com.example.security.Models.Course;
import com.example.security.Request.CourseRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaSpecificationExecutor<Course>, JpaRepository<Course,Integer> {

    //WE CAN USE THIS OR USE SPECIFICATION CLASS
    @Modifying
    @Transactional
    @Query("select Course from Course where title=:course_name")
    void getCourse(String course_name);

    Page<Course> findCourseByTitleLike(String title, Pageable pageable);

    //List<Course> getAllByTitleLike(String title);
    Course getCourseById (int id);

   List<Course> getCoursesByStudentsId (int id);


    @Modifying
    @Transactional
    @Query("SELECT c FROM Course c JOIN c.students s where s.id=:studentId")
    List<Course> ddd (@Param("studentId") Long studentId );

List<Course> findAllByStudentsId(int id);

   // Course findAllByStudentsId(int id);


}
