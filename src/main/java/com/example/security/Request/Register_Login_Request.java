package com.example.security.Request;

import com.example.security.annotation.ValidPassword;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Register_Login_Request {


    @NotEmpty(message = "The name should not be empty")
    @NotBlank(message = "The name should not be Blank")

    private String name;
  //  @NotNull(message = "The email should not be null")
  //  @NotEmpty(message = "The email should not be empty")

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE)

    private String email;
    @ValidPassword
    private String password ;




}
