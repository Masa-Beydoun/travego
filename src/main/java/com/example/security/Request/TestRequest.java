package com.example.security.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestRequest {
    private int id;
    private MultipartFile multipartFile;
    private double my_Money;
    private String method;
    private  String amount;
    private String currency;
    private  String description;
    private String codeNumber;
    private Integer user_Id;

}
