package SpringBootStarterProject.ManagingPackage.Paypal;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.email.EmailService;
import SpringBootStarterProject.ManagingPackage.email.EmailStructure;
import SpringBootStarterProject.UserPackage.Models.TransactionHistory;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import SpringBootStarterProject.UserPackage.Repositories.TransactionRepository;
import SpringBootStarterProject.UserPackage.Repositories.WalletRepository;
import SpringBootStarterProject.UserPackage.Request.TransactionHistoryDto;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaypalController {

    private final PaypalService paypalService;
    private final EmailService emailService;
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;


    @GetMapping("home")
    public String home() {
        return "index";
    }

    @PostMapping("/payment/create")
    public RedirectView createPayment(
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description
    ) {
        try {
            String cancelUrl = "http://localhost:8080/payment/cancel";
            String successUrl = "http://localhost:8080/payment/success";
            Payment payment = paypalService.createPayment(
                    Double.valueOf(amount),
                    currency,
                    method,
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                //TODO:: ADD PAYMENT METHOD HERE
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        return "paymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }

    @PostMapping("PayPal/payment_Done")
    public ApiResponseClass payment_succeded(@RequestBody TransactionHistoryDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new NoSuchElementException("EMAIL NOT FOUND"));

        var transaction = TransactionHistory.builder()
                .transactionAmmount(request.getTransactionAmmount())
                .type(request.getType())
                .date(request.getDate())
                .description("payment succeded")
                .status(request.getStatus())
                .relationshipId(request.getRelationshipId())
                .wallet(client.getWallet())
                .build();
        transactionRepository.save(transaction);
        EmailStructure emailStructure=EmailStructure.builder()
                .subject("Payment Throw PAYPAL Done Successfully")
                .message("Mr. "+client.getFirst_name() +", Payment Throw PAYPAL Done Successfully " )
                .build();
        emailService.sendMail(client.getEmail(),emailStructure);
        return new ApiResponseClass("payment succeded", HttpStatus.ACCEPTED, LocalDateTime.now(), transaction);

    }

}
