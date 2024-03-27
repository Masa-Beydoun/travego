package com.example.security.Controller;

import com.example.security.Models.Course;
import com.example.security.Paypal.PaypalController;
import com.example.security.Paypal.PaypalService;
import com.example.security.Repository.StudentRepository;
import com.example.security.Request.CourseRequest;
import com.example.security.Request.StudentRegisterRequest;
import com.example.security.Request.TestRequest;
import com.example.security.Security.auth.AuthenticationResponse;
import com.example.security.Service.StudentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Student")
public class StudentController {
    private final StudentService studentService;
    private final PaypalService paypalService;



    @PostMapping("/Add_Course_To_my_account")

    public ResponseEntity<String>Add_Course_To_my_account(@RequestBody StudentRegisterRequest request) throws PayPalRESTException {

        String cancelUrl = "http://localhost:8070/payment/cancel";
        String successUrl = "http://localhost:8070/payment/success";
        Payment payment = paypalService.createPayment(
                Double.valueOf(10.0),
                "USD",
                "paypal",
                "sale",
                "description",
                cancelUrl,
                successUrl
        );
        PaymentExecution paymentExecution=new PaymentExecution();
      //  paymentExecution.setPayerId();

        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {

                studentService.Add_Course_To_my_account(request.getCourse_id());
                return ResponseEntity.ok().body("COURSE ADDED SUCCSUESSFULLY"+links.getRel()+"  "+payment.getId()+"        "+
                payment.getPayer());
            }

        }
        return ResponseEntity.badRequest().body("COURSE not addedd");
    }

    @GetMapping("Get_My_Courses")
    public ResponseEntity<?> Get_My_Courses(@RequestBody TestRequest request)
    {
       return ResponseEntity.ok(studentService.Get_My_Courses(request.getId()));
    }

    @PostMapping("add_money_to_myaccount")
    public ResponseEntity<?> add_money_to_myaccount(@RequestBody TestRequest request)
    {
        studentService.add_money_to_myaccount(request.getMy_Money());
        return ResponseEntity.ok().body("MONEY ADDED TO WALLET");
    }
// GET ALL AUTHORS ,, ALL STUDENTS
}
