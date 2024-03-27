package com.example.security.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateLectureRequest {

    @NotEmpty(message = "name of the lecture is empty")
    private String name;
    private int course_id;
}
