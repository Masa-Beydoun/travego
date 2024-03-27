package com.example.security.Repository;

import com.example.security.Models.Course;
import com.example.security.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {

public Student getStudentsById(Integer id);
}
