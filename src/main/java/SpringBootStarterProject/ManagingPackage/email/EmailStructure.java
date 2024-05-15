package com.example.security.email;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class EmailStructure {
    private String subject;
    private String message;

}
