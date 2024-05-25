package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.ManagingPackage.Response.ApiTest;
import SpringBootStarterProject.ManagingPackage.Security.Token.Token;
import SpringBootStarterProject.ManagingPackage.Security.Token.TokenType;
import SpringBootStarterProject.UserPackage.Models.Client;
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


    @GetMapping("Test")
    public List<ApiTest>Test()
    {

        List<ApiTest> list=new ArrayList();
        var token= Token.builder().token("s").tokenType(TokenType.BEARER)
                .RelationId(1)
                .expired(false)
                .revoked(false)
                .id(1)
                .build();
        Map<String,Object> map=new HashMap<>();
        map.put("key",token);
        map.put("key","samer");
              list.add( new ApiTest("message", HttpStatus.ACCEPTED, LocalDateTime.now()));
            list.add( new ApiTest("message", HttpStatus.ACCEPTED, LocalDateTime.now(),token));
        list.add( new ApiTest("message", HttpStatus.ACCEPTED, LocalDateTime.now(),map));
        return list;

    }
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


    @GetMapping("/Add_Client_Visa")
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

//    @PostMapping("/Add_Client_Passenger")
//    private ResponseEntity<?> AddMyPassenger (@RequestBody VisaRequest request)
//    {
//        return ResponseEntity.ok(clinetAccountService.AddMyPassengers(request));
//    }


//    @GetMapping("/Get_Client_Passenger")
//    private ResponseEntity<?> GetMyPassenger (@RequestBody VisaRequest request)
//    {
//        return ResponseEntity.ok(clinetAccountService.GetMyPassenger(request));
//    }




}
