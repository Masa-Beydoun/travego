package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.UserPackage.Request.LoginRequest;
import SpringBootStarterProject.UserPackage.Request.ManagerRegisterRequest;
import SpringBootStarterProject.UserPackage.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Auth/Manager")
@RequiredArgsConstructor
public class ManagerController
{

    private final AuthService authService;
        @PostMapping("/Promote_To_Manager")
    private ResponseEntity<?> PromoteToManager (@RequestBody ManagerRegisterRequest request)
    {
        // validator.validate(request);
        return authService.PromoteToManager(request);
    }

    @PostMapping("/Manager_Login")
    private ResponseEntity<?> SuperVisorLogin (@RequestBody LoginRequest request)
    {
        // validator.validate(request);
        return authService.ManagerLogin(request);
    }


}
