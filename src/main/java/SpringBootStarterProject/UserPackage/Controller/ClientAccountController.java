package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.UserPackage.Request.ClientDetailsRequest;
import SpringBootStarterProject.UserPackage.Request.ClientRegisterRequest;
import SpringBootStarterProject.UserPackage.Request.EditClientRequest;
import SpringBootStarterProject.UserPackage.Services.ClinetAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/CLient/Account")
@RequiredArgsConstructor
public class ClientAccountController
{
    private final ClinetAccountService clinetAccountService;

    @GetMapping("Get_My_Account")
    public ResponseEntity<?>GetMyAccount()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyAccount());

    }

    @PutMapping("Edit_My_Account")
    public ResponseEntity<?>EditMyAccount(@RequestBody EditClientRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.EditMyAccount(request));

    }

    @PostMapping("/Add_Client_Details")
    private ResponseEntity<?> AddMyDetails (@RequestBody ClientDetailsRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyDetails(request));
    }

}
