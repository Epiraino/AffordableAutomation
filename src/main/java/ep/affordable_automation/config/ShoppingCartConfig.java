package ep.affordable_automation.config;

import ep.affordable_automation.domain.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@ControllerAdvice
public class ShoppingCartConfig {

    @Bean
    public ShoppingCart shoppingCart() {
        return new ShoppingCart();
    }

}