package ep.affordable_automation.controller;

import ep.affordable_automation.domain.CartItem;
import ep.affordable_automation.domain.ShoppingCart;
import ep.affordable_automation.model.ProductsDTO;
import ep.affordable_automation.service.ProductsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShoppingCartController {

    private final ShoppingCart shoppingCart;

    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("cartItems", shoppingCart.getItems());
        model.addAttribute("cartTotal", shoppingCart.getTotal());
        return "shopping-cart";
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
