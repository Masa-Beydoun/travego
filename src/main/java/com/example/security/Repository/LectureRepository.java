package com.example.security.Repository;

import com.example.security.Models.Course;
import com.example.security.Models.Lecture;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture,Integer> , JpaSpecificationExecutor<Lecture> {

    public ResponseEntity<?> findByNameLike(String name);

    //IT SHOULD BE COURSE ID OR NAME?
    public ResponseEntity<?> getLectureByCourse (Course course);

    //ALL Course LectureSSSSS
    public List<Lecture> getLecturesByCourse (Course course);
    //@Transactional
    //@Modifying
  // @Query("select Lecture from Lecture where course=:course")
  public Page<Lecture> getLecturesByCourse (Course course, Pageable pageable);

    public List<Lecture> getLecturesByIdIn (List<Integer> id);
  //  @Modifying

    public Lecture getLectureByIdAndCourse(int lecture_id,Course course);

    public Page<Lecture> getLecturesByNameLike(String lecture_Name,Pageable pageable);

}
