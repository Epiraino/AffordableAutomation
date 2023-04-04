package ep.affordable_automation.controller;

public class ShoppingCartController {
    //    @GetMapping("/shopping-cart")
//    public String viewCart(Model model, HttpSession session) {
//        ShoppingCart cart = getCart(session);
//        if (cart != null) {
//            model.addAttribute("cartItems", cart.getItems());
//        }
//        // Add other necessary attributes like the total price
//        return "shoppingcart";
//    }
//
//    @GetMapping("/add-to-cart/{productId}")
//    public String addToCart(@PathVariable("productId") Integer productId, HttpSession session) {
//        ShoppingCart cart = getCart(session);
//        ProductsDTO product = productsService.get(productId);
//        CartItem item = new CartItem(product, 1);
//        cart.addItem(item);
//        return "redirect:/products";
//    }
//
//    @GetMapping("/remove-from-cart/{productId}")
//    public String removeFromCart(@PathVariable("productId") Integer productId, HttpSession session) {
//        ShoppingCart cart = getCart(session);
//        ProductsDTO product = productsService.get(productId);
//        CartItem itemToRemove = null;
//        for (CartItem item : cart.getItems()) {
//            if (item.getProduct().getProductId().equals(productId)) {
//                itemToRemove = item;
//                break;
//            }
//        }
//        if (itemToRemove != null) {
//            cart.removeItem(itemToRemove);
//        }
//        return "redirect:/shoppingcart";
//    }
//
//
//    private ShoppingCart getCart(HttpSession session) {
//        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
//        if (cart == null) {
//            cart = new ShoppingCart();
//            session.setAttribute("shoppingCart", cart);
//        }
//        return cart;
//    }
}
