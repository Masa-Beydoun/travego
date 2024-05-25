package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.ManagingPackage.Security.Token.Token;
import SpringBootStarterProject.ManagingPackage.Security.Token.TokenType;
import SpringBootStarterProject.UserPackage.Request.*;
import SpringBootStarterProject.UserPackage.Services.ClinetAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/Add_Client_Passport")
    private ResponseEntity<?> AddMyPassport (@RequestBody PassportRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyPassport(request));
    }

    @PostMapping("/Add_Client_Personalid")
    private ResponseEntity<?> AddMyPersonalid (@RequestBody PersonalidRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyPersonalid(request));
    }


    @PostMapping("/Add_Client_Visa")
    private ResponseEntity<?> AddMyVisa (@RequestBody VisaRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyVisa(request));
    }

    @GetMapping("/Get_My_Passport")
    private ResponseEntity<?> GetMyPassport ()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyPassport());
    }


    @GetMapping("/Get_My_PersonalId")
    private ResponseEntity<?> GetMyPersonalId ()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyPersonalId());
    }

    @GetMapping("/Get_My_Visa")
    private ResponseEntity<?> GetMyVisa ()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyVisa());
    }


    @DeleteMapping("/Delete_My_Passport")
    private ResponseEntity<?> Delete_My_Passport ()
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyPassport());
    }


    @DeleteMapping("/Delete_My_PersonalId")
    private ResponseEntity<?> Delete_My_PersonalId ()
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyPersonalId());
    }

    @DeleteMapping("/Delete_My_Visa")
    private ResponseEntity<?> Delete_My_Visa ()
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyVisa());
    }





    @PostMapping("/Add_Client_Passenger")
    private ResponseEntity<?> AddMyPassenger (@RequestBody AddPassengerRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyPassengers(request));
    }


    @GetMapping("/Get_Client_Passenger/{id}")
    private ResponseEntity<?> GetOnePassenger (@PathVariable Integer id)
    {
        return ResponseEntity.ok(clinetAccountService.GetOnePassenger(id));
    }


    @GetMapping("/Get_All_Client_Passengers")
    private ResponseEntity<?> GetMyAllPassengers ()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyAllPassengers());
    }


        @PutMapping("/Edit_Client_Passenger")
    private ResponseEntity<?> EditClientPassenger (@RequestBody AddPassengerRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.EditClientPassenger(request));
    }



        @DeleteMapping("/Delete_Client_Passenger/{id}")
    private ResponseEntity<?> DeleteMyPassenger (@PathVariable Integer id)
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyPassenger(id));
    }




}
