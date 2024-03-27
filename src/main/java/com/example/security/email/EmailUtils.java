package com.example.security.email;

import com.example.security.Controller.AuthorController;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailUtils {
private final AuthorController authorController;

    public static  String getEmailMessageWithCode(String name,String host, String code)
    {
        return "Hello " + name + ",\n\nYour new account has been created. this is your code  \n\n" +
                code;
    }
    public static  String getEmailMessage(String name,String host, String token)
    {
        return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(host,token)+"\n\nthe support Team";
    }
    public static String getVerificationUrl(String host,String token)
    {

        return host+"/Author?token="+token;
    }
}
