package com.PayPal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paypal")
public class WebController {
    @GetMapping("/")
    public String home() {
        return "Payment";
    }

    /*@GetMapping("/success")
    public String success() {
        return "Payment Success";
    }*/

    @GetMapping("/cancel")
    public String cancel() {
        return "Payment Cancel";
    }

}
