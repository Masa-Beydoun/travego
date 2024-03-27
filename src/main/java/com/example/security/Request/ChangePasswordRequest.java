package com.example.security.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String repeatPassword;
}
