package SpringBootStarterProject.UserPackage.Controller;

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

//    @PutMapping("Edit_My_Account")
//    public ResponseEntity<?> EditMyAccoutn(@RequestBody EditClientRequest request)
//    {
//        return ResponseEntity.ok(clinetAccountService.EditMyAccoutn());
//    }
//    @PostMapping("/Add_Client_Details")
//    private ResponseEntity<?> AddClientDetails (@RequestBody ClientRegisterRequest request)
//    {
//        // validator.validate(request);
//       // return authService.ClientRegister(request);
//    }

}
