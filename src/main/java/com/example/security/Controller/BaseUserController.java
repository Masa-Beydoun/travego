package com.example.security.Controller;

import com.example.security.Request.*;
import com.example.security.Security.auth.AuthenticationResponse;
import com.example.security.Service.AuthorService;
import com.example.security.Service.BaseUserService;
import com.example.security.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/BaseUser")
@RestController
@RequiredArgsConstructor
public class BaseUserController {
    private final BaseUserService baseUserService;

    private final StudentService studentService;
    private final AuthorService authorService;


    @GetMapping("/showMap")
    public String index() {
        return "index";
    }

    //DONE
    @PostMapping("/Register")

    public ResponseEntity<?> Author_Register(@RequestBody Register_Login_Request request) {
        return ResponseEntity.ok(authorService.Register_with_ConfCode(request));
    }


@PostMapping("/send_Confirmation_Code")
public ResponseEntity<?>send_Confirmation_Code(@RequestBody TestRequest request )
{
return ResponseEntity.ok().body(baseUserService.checkCodeNumber(request.getCodeNumber(),request.getUser_Id()));
}
    @PostMapping("/Regenerate_Confirmation_Code")
    private void RegenerateToken(@RequestBody TestRequest request)
    {
        authorService.RegenerateToken(request);
        ResponseEntity.ok().body("CODE SENT SUCCSESSFULLY");
    }
    @PostMapping("/Student/Register")
    public ResponseEntity<AuthenticationResponse> Student_Register(@RequestBody Register_Login_Request request)
    {
        return ResponseEntity.ok(studentService.Register(request));
    }
    //DONE
    @PostMapping( "/Login")
    public ResponseEntity<?> Login(@RequestBody Register_Login_Request request)
    {
        return ResponseEntity.ok(baseUserService.Login(request));
    }


    @PostMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal ConnectedUser)
    {
      baseUserService.changePassword(request,ConnectedUser);
      return ResponseEntity.ok("Password changed succsessfully");
    }

    @GetMapping( "/MyProfile")
    public ResponseEntity<?> MyProfile()
    {
        return ResponseEntity.ok(baseUserService.MyProfile());
    }

    @PostMapping("/Edit_Profile")
    public ResponseEntity<?> Edit_Profile(@RequestBody EditRequest request)
    {
        baseUserService.Edit_Profile(request);
       return ResponseEntity.ok().body("Profile edited succsessfully");
    }

}
