package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.UserPackage.Request.EmailConfirmationRequest;
import SpringBootStarterProject.UserPackage.Request.LoginRequest;
import SpringBootStarterProject.UserPackage.Request.ClientRegisterRequest;
import SpringBootStarterProject.UserPackage.Services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Auth")
@RequiredArgsConstructor
public class AuthController {

   private final AuthService authService;
    @PostMapping("/Client_Register")
    private ResponseEntity<?> ClientRegister (@RequestBody ClientRegisterRequest request)
    {
       // validator.validate(request);
        return authService.ClientRegister(request);
    }

    @PostMapping("/Client_Login")
    private ResponseEntity<?> ClientLogin (@RequestBody LoginRequest request, HttpServletRequest httpServletRequest)
    {
        // validator.validate(request);


       return ResponseEntity.ok().body(authService.ClientLogin(request,httpServletRequest));
    }

    @PostMapping("/Client_Check_Code")
    private ResponseEntity<?> checkCodeNumber (@RequestBody EmailConfirmationRequest request)
    {
        // validator.validate(request);


        return ResponseEntity.ok().body(authService.checkCodeNumber(request));
    }


    @PostMapping("/Regenerate_Confirmation_Code")
    private ResponseEntity<?> RegenerateConfCode (@RequestBody LoginRequest request)
    {
        // validator.validate(request);


        return ResponseEntity.ok().body(authService.RegenerateConfCode(request));
    }


//    @PostMapping("/Admin_Login")
//    private ResponseEntity<?> AdminLogin (@RequestBody LoginRequest request)
//    {
//        // validator.validate(request);
//        return authService.AdminLogin(request);
//    }

}
