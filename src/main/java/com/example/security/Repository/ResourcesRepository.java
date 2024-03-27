package com.example.security.Repository;

import com.example.security.Models.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ResourcesRepository extends JpaRepository<Resources,Integer> {

  // public ResponseEntity<?> getResourcesByLectureId (long lecture_id);

//    public Resources getResourcesByLectureId (int lecture_id);
    public List<Resources> getResourcesByLectureIdIn (List<Integer> lecture_id);

    public List<Resources> getResourcesByIdIn (List<Integer> resources_Id);
    public Resources getResourcesByLectureId (Integer lecture_Id);


    Optional<Resources> findByName(String fileName);

}
