package ep.affordable_automation.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import ep.affordable_automation.domain.CartItem;
import ep.affordable_automation.service.ProductsService;
import ep.affordable_automation.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private StripeService stripeService;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    @PostMapping("/checkout")
    public String createCheckoutSession(@RequestBody List<CartItem> cartItems) {
        try {
            Session session = stripeService.createCheckoutSession(cartItems, successUrl, cancelUrl);
            return session.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException("Error creating Stripe checkout session", e);
        }
    }

    // Add other ShoppingCart related endpoints here
}