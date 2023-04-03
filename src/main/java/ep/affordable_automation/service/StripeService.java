package ep.affordable_automation.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.billingportal.Session;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import ep.affordable_automation.domain.CartItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StripeService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public PaymentIntent createPaymentIntent(int amount, String currency) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCurrency(currency)
                .setAmount((long) amount)
                .build();

        return PaymentIntent.create(params);
    }
    public PaymentIntent confirmPaymentIntent(String paymentIntentId, String paymentMethodId) throws StripeException {
        PaymentIntentConfirmParams params = PaymentIntentConfirmParams.builder()
                .setPaymentMethod(paymentMethodId)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        return paymentIntent.confirm(params);
    }
    public Session createCheckoutSession(List<CartItem> cartItems, String successUrl, String cancelUrl) throws StripeException {
        SessionCreateParams.LineItem[] lineItems = cartItems.stream().map(item -> SessionCreateParams.LineItem.builder()
                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("usd")
                        .setUnitAmount(item.getUnitPrice())
                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(item.getName())
                                .setDescription(item.getDescription())
                                .build())
                        .build())
                .setQuantity((long) item.getQuantity())
                .build()).toArray(SessionCreateParams.LineItem[]::new);

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setLineItems(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .build();

        return Session.create(params);
    }

    // Add other Stripe-related methods as needed
}