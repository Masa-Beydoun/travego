package com.example.security.Request;

import com.example.security.Models.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCourseRequest {
    private int id;

    private String title;

    private String description;

    private Double price;

    private String authorname;
}
