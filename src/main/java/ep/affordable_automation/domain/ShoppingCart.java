package ep.affordable_automation.domain;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import com.stripe.model.Product;
import com.stripe.model.Source;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;


@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ShoppingCart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        for (CartItem i : items) {
            if (i.getProduct().getProductId().equals(item.getProduct().getProductId())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem item : items) {
            BigDecimal price = item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalPrice = totalPrice.add(price);
        }
        return totalPrice;
    }
}