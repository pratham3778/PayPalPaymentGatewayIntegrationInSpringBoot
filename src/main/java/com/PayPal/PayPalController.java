package com.PayPal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paypal")
@CrossOrigin("http://localhost:63342/")
public class PayPalController {
    @Autowired
    private PayPalService payPalService;

    private static final  String SUCCESS_URL = "http://localhost:8080/paypal/success";
    private static final String CANCEL_URL = "http://localhost:8080/paypal/cancel";

    @PostMapping("/pay")
    public String makePayment(@RequestParam double amount) {
        try {
            Payment payment = payPalService.createPayment(
                    amount,
                    "USD",
                    "paypal",
                    "sale",
                    "payment description",
                    CANCEL_URL,
                    SUCCESS_URL);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "Redirect to: " + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

        return "Error processing the payment";
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId) throws PayPalRESTException {
        try {
            Payment payment = payPalService.execute(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "Payment is successfully done";
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
        return "Payment Failed";
    }

}
