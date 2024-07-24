package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.UserPackage.Request.*;
import SpringBootStarterProject.UserPackage.Services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/Auth/Manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/Add_Admin_ToSystem")
    private ResponseEntity<?> AddAdminToSystem(@RequestBody ManagerRegisterRequest request) {
        // validator.validate(request);
        return ResponseEntity.ok(managerService.AddAdminToSystem(request));
        //  return authService.PromoteToManager(request);
    }

    @PostMapping("/Manager_Login")
    private ResponseEntity<?> ManagerLogin(@RequestBody LoginRequest request) {
        // validator.validate(request);
        return ResponseEntity.ok(managerService.ManagerLogin(request));
        //  return authService.ManagerLogin(request);
    }

    @GetMapping("Get_AllAdmins")
    private ResponseEntity<?> GetAllAdmins() {
        return ResponseEntity.ok(managerService.GetAllAdmins());
    }

    @GetMapping("Get_AllClient")
    private ResponseEntity<?> GetAllClient() {
        return ResponseEntity.ok(managerService.GetAllClient());
    }

    @PostMapping("Create_CLient")
    private ResponseEntity<?> CreateClient(@RequestBody ClientRegisterRequest request) {
        return ResponseEntity.ok(managerService.CreateClinet(request));
    }

    @DeleteMapping("Delete_CLient")
    private ResponseEntity<?> DeleteClient(@RequestBody EmailRequest request) {
        return ResponseEntity.ok(managerService.DeleteClient(request));
    }
    //fix delete Admin
    @DeleteMapping("Delete_Manager")
    private ResponseEntity<?> DeleteManager(@RequestBody EmailRequest request) {
        return ResponseEntity.ok(managerService.DeleteManager(request));
    }
    //fix  Admin
    @PostMapping("Manager_Activation")
    private void ManagerActivation(@RequestBody EmailRequest request) {
        managerService.ManagerActivation(request);

    }

    @PostMapping("Client_Activation")
    private void ClientActivation(@RequestBody EmailRequest request) {
        managerService.ClientActivation(request);

    }

    @PutMapping("Edit_Client")
    private ResponseEntity<?> EditClient(@RequestBody EditClientRequest request) {
        return ResponseEntity.ok(managerService.EditClient(request));

    }
    //fix  SuperAdmin
    @PutMapping("Edit_Manager")
    private ResponseEntity<?> EditManager(@RequestBody EditManagerRequest request) {
        return ResponseEntity.ok(managerService.EditManager(request));

    }

    @PostMapping("/Manager_Change_Password")
    private ResponseEntity<?> ManagerChangePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) {
        // validator.validate(request);

        return ResponseEntity.ok(managerService.ManagerChangePassword(request, connectedUser));
    }

    @GetMapping("/Get_All_Reservation_Request_For_Trip/{Trip_Id}")
    private ResponseEntity<?> GetAllReservationRequestForTrip(@PathVariable Integer Trip_Id) {
        // validator.validate(request);

        return ResponseEntity.ok(managerService.GetAllReservationRequestForTrip(Trip_Id));
    }
    @PostMapping("/Edit_Reservation_Request_Statue_For_Trip/{Trip_Id}")
    private ResponseEntity<?> EditReservationRequestStatueForTrip(@RequestBody ConfirmationPassengerInTripRequest request) {
        // validator.validate(request);

        return ResponseEntity.ok(managerService.EditReservationRequestStatueForTrip(request));
    }

}
