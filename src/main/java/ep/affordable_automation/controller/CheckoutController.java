package ep.affordable_automation.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import ep.affordable_automation.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CheckoutController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/checkout")
    public String handleCheckout(HttpServletRequest request) {
        String stripeToken = request.getParameter("stripeToken");
        int amount = 100; // The total amount to charge, in cents
        String currency = "usd";

        try {
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount, currency);
            stripeService.confirmPaymentIntent(paymentIntent.getId(), stripeToken);
        } catch (StripeException e) {
            // Handle the exception (e.g., show an error message)
            e.printStackTrace();
            return "error";
        }

        // Redirect to a success page or show a success message
        return "success";
    }
}
