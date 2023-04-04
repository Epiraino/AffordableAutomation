package ep.affordable_automation.domain;

import ep.affordable_automation.model.ProductsDTO;

import java.math.BigDecimal;

public class CartItem {

    private ProductsDTO product;
    private int quantity;
    private BigDecimal totalPrice;

    public CartItem(ProductsDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice().multiply(new BigDecimal(quantity));
    }

    // Getters and setters

    public ProductsDTO getProduct() {
        return product;
    }

    public void setProduct(ProductsDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}