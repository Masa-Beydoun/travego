package com.example.security.Request;

import com.example.security.Models.Author;
import com.example.security.Models.Course;
import com.example.security.Models.Lecture;
import com.example.security.Models.Student;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseRequest {

   //FOR REQUESTING BY ID

    private int id;
    @NotNull(message = "The title should not be null")
    @NotEmpty(message = "The title should not be empty")
    private String title;
    @NotNull(message = "The description should not be null")
    @NotEmpty(message = "The description should not be empty")
    private String description;
    @NotNull(message = "The price should not be null")

    private Double price;

 @NotNull(message = "The s should not be null")

 private Double s;

    private Author author;

    private List<Lecture> lectures;

    private Student student;

    private Course course;


 public CourseRequest(Course course) {
  this.course=course;
 }
}
