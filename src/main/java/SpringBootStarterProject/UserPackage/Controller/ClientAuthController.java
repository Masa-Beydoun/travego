package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.UserPackage.Request.EmailConfirmationRequest;
import SpringBootStarterProject.UserPackage.Request.LoginRequest;
import SpringBootStarterProject.UserPackage.Request.ClientRegisterRequest;
import SpringBootStarterProject.UserPackage.Request.EmailRequest;
import SpringBootStarterProject.UserPackage.Services.ClientAuthService;
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
public class ClientAuthController {

   private final ClientAuthService authService;
    @PostMapping("/Client_Register")
    private ResponseEntity<?> ClientRegister (@RequestBody ClientRegisterRequest request)
    {
       // validator.validate(request);
       // return ;
        return ResponseEntity.ok(authService.ClientRegister(request));
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


        return ResponseEntity.ok((authService.checkCodeNumber(request)));
    }

//TODO :: FIX LOGING REQUEST DTO ISSUE
    @PostMapping("/Regenerate_Confirmation_Code")
    private ResponseEntity<?> RegenerateConfCode (@RequestBody EmailRequest request)
    {
        // validator.validate(request);


        return ResponseEntity.ok(authService.RegenerateConfCode(request));
    }




}
