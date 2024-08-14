package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.UserPackage.Request.*;
import SpringBootStarterProject.UserPackage.Services.ClientAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/Auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ClientAuthController {

   private final ClientAuthService clientAuthService;
    @PostMapping("/Client_Register")
    private ResponseEntity<?> ClientRegister (@RequestBody ClientRegisterRequest request)
    {
       // validator.validate(request);
       // return ;
        return ResponseEntity.ok(clientAuthService.ClientRegister(request));
    }

    @PostMapping("/Client_Login")
    private ResponseEntity<?> ClientLogin (@RequestBody LoginRequest request, HttpServletRequest httpServletRequest)
    {
        // validator.validate(request);


       return ResponseEntity.ok().body(clientAuthService.ClientLogin(request,httpServletRequest));
    }

    @PostMapping("/Client_Check_Code")
    private ResponseEntity<?> checkCodeNumber (@RequestBody EmailConfirmationRequest request)
    {
        // validator.validate(request);


        return ResponseEntity.ok((clientAuthService.checkCodeNumber(request)));
    }

    //TODO :: FIX LOGING REQUEST DTO ISSUE
    @PostMapping("/Regenerate_Confirmation_Code")
    private ResponseEntity<?> RegenerateConfCode (@RequestBody EmailRequest request)
    {
        // validator.validate(request);


        return ResponseEntity.ok(clientAuthService.RegenerateConfCode(request));
    }


    @PostMapping("/GenerateForgetPasswordCode")
    private ResponseEntity<?> GenerateForgetPasswordCode (@RequestBody EmailRequest request)
    {
        // validator.validate(request);

        return ResponseEntity.ok(clientAuthService.GenerateForgetPasswordCode(request));
    }

    @PostMapping("/ForgetPassword")
    private ResponseEntity<?> ForgetPassword (@RequestBody ForgetPasswordRequest request)
    {
        // validator.validate(request);

        return ResponseEntity.ok(clientAuthService.ForgetPassword(request));
    }


}
