package com.example.security.Request;

import com.example.security.Models.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourcesRequest {

    //FOR REQUESTING BY ID
    private int id;

    private int lecture_id;

    private String name;

    private long size;

    private String url;

    private Lecture lecture;

    private Course course;

    //FOR FILES
    private String type;



    //FOR TEXT
    private String content;

    //FOR VIDEO
    private int length;

    private Resources resources;



    private MultipartFile text;

    private MultipartFile video;

    private MultipartFile file;

}
