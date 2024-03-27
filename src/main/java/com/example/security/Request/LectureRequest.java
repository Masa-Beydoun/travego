package com.example.security.Request;

import com.example.security.Models.Course;
import com.example.security.Models.Resources;
import jakarta.validation.constraints.NotEmpty;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LectureRequest {

    //FOR REQUESTING BY ID
    private int id;

    private String name;
    private int course_id;
    private Resources resources;
    private String type;
    private String content;
    private int length;
    private List<Integer> lecturers_Id;

    private List<Integer> resources_Id;
    private Integer lecture_Id;
private Course course;
}
