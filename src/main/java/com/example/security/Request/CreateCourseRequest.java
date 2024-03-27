package com.example.security.Request;

import com.example.security.Models.Author;
import com.example.security.Models.Lecture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCourseRequest {


    @NotEmpty(message = "The title should not be empty")
    @NotBlank(message = "The title should not be Blank")
    private String title;


    @NotEmpty(message = "The description should not be empty")
    @NotBlank(message = "The description should not be Blank")
    private String description;

    @NotNull(message = "The price should not be null")
    private Double price;




}
