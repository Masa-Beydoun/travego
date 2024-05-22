package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.UserPackage.Request.*;
import SpringBootStarterProject.UserPackage.Services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Auth/Manager")
@RequiredArgsConstructor
public class ManagerController
{

    private final ManagerService managerService;
        @PostMapping("/Add_Admin_ToSystem")
    private ResponseEntity<?> AddAdminToSystem (@RequestBody ManagerRegisterRequest request)
    {
        // validator.validate(request);
        return ResponseEntity.ok(managerService.AddAdminToSystem(request));
      //  return authService.PromoteToManager(request);
    }

    @PostMapping("/Manager_Login")
    private ResponseEntity<?> ManagerLogin (@RequestBody LoginRequest request)
    {
        // validator.validate(request);
        return ResponseEntity.ok(managerService.ManagerLogin(request));
      //  return authService.ManagerLogin(request);
    }
    @GetMapping("Get_AllAdmins")
    private ResponseEntity<?> GetAllAdmins()
   {
       return ResponseEntity.ok(managerService.GetAllAdmins());
   }

    @GetMapping("Get_AllClient")
    private ResponseEntity<?> GetAllClient()
    {
        return ResponseEntity.ok(managerService.GetAllClient());
    }

    @PostMapping("Create_CLient")
    private ResponseEntity<?> CreateClient(@RequestBody ClientRegisterRequest request)
    {
        return ResponseEntity.ok(managerService.CreateClinet(request));
    }

    @DeleteMapping("Delete_CLient")
    private ResponseEntity<?> DeleteClient(@RequestBody EmailRequest request)
    {
        return ResponseEntity.ok(managerService.DeleteClient(request));
    }
    @DeleteMapping("Delete_Manager")
    private ResponseEntity<?> DeleteManager(@RequestBody EmailRequest request)
    {
        return ResponseEntity.ok(managerService.DeleteManager(request));
    }

    @PostMapping("Manager_Activation")
    private void ManagerActivation(@RequestBody EmailRequest request)
    {
        managerService.ManagerActivation(request);

    }

    @PostMapping("Client_Activation")
    private void ClientActivation(@RequestBody EmailRequest request)
    {
        managerService.ClientActivation(request);

    }

    @PutMapping("Edit_Client")
    private ResponseEntity<?> EditClient(@RequestBody EditClientRequest request)
    {
    return ResponseEntity.ok(managerService.EditClient(request));

    }

    @PutMapping("Edit_Manager")
    private ResponseEntity<?> EditManager(@RequestBody EditManagerRequest request)
    {
        return ResponseEntity.ok( managerService.EditManager(request));

    }

}
