package ep.affordable_automation.controller;


import ep.affordable_automation.domain.CartItem;
import ep.affordable_automation.model.ProductsDTO;
import ep.affordable_automation.service.ProductsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductsController {

    private final ProductsService productsService;


    public ProductsController(final ProductsService productsService) {
        this.productsService = productsService;

    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        List<ProductsDTO> products = productsService.findAll();
        model.addAttribute("products", products);
        return "products";
    }
}