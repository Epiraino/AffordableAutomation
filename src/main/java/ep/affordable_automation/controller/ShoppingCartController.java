package ep.affordable_automation.controller;

import ep.affordable_automation.domain.CartItem;
import ep.affordable_automation.domain.Products;
import ep.affordable_automation.domain.ShoppingCart;
import ep.affordable_automation.model.ProductsDTO;
import ep.affordable_automation.service.ProductsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ShoppingCartController {

    private final ProductsService productsService;
    private final ShoppingCart shoppingCart;

    public ShoppingCartController(final ProductsService productsService,final ShoppingCart shoppingCart) {
        this.productsService = productsService;
        this.shoppingCart = shoppingCart;
    }

    @GetMapping("/shopping-cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", shoppingCart.getItems());
        // Add other necessary attributes like the total price
        return "shoppingcart";
    }

    @GetMapping("/add-to-cart/{productId}")
    public String addToCart(@PathVariable("productId") Integer productId, HttpSession session) {
        ShoppingCart cart = getCart(session);
        ProductsDTO product = productsService.get(productId);
        CartItem item = new CartItem(product, 1);
        cart.addItem(item);
        return "redirect:/products";
    }

    @GetMapping("/remove-from-cart/{productId}")
    public String removeFromCart(@PathVariable("productId") Integer productId, HttpSession session) {
        ShoppingCart cart = getCart(session);
        ProductsDTO product = productsService.get(productId);
        CartItem itemToRemove = null;
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getProductId().equals(productId)) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            cart.removeItem(itemToRemove);
        }
        return "redirect:/shoppingcart";
    }

    private ShoppingCart getCart(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("shoppingCart", cart);
        }
        return cart;
    }
}
