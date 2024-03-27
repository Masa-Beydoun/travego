package com.example.security.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentRegisterRequest {
    private String name;
    private String email;
    private String password;
    private int course_id;
}
