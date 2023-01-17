package com.course.client.controllers;

import com.course.client.beans.*;
import com.course.client.proxies.MsCartProxy;
import com.course.client.proxies.MsOrderProxy;
import com.course.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;
    @Autowired
    private MsCartProxy msCartProxy;
    @Autowired
    private MsOrderProxy msOrderProxy;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("products", msProductProxy.list());
        return "index";
    }
    @RequestMapping("product-detail/{productId}")
    public String infoProduit(Model model, @PathVariable(value = "productId") Long productId) {
        try{
            model.addAttribute("product", msProductProxy.get(productId).get());
        } catch (Exception e) {
            model.addAttribute("exception", e);
            return "error";
        }
        return "productDetail";
    }

    public void createCartIfNoCart(){
        if(!msCartProxy.getCart(msCartProxy.getCartId()).isPresent())
            msCartProxy.createNewCart(new CartBean());
    }
    @RequestMapping("cart")
    public String getCart(Model model) {
        createCartIfNoCart();
        List<CartItemBean> cartItems = msCartProxy.getCart(msCartProxy.getCartId()).get().getProducts();
        List<ProductBean> products = msProductProxy.list();
        double totalPrix = 0.0;
        int totalProduits = 0;
        for(CartItemBean cib : cartItems){
            totalPrix += cib.getTotalPrice(products);
            totalProduits+=cib.getQuantity();
        }
        model.addAttribute("cartitems", cartItems);
        model.addAttribute("products", products);
        model.addAttribute("totalPrice", totalPrix);
        model.addAttribute("totalProduits", totalProduits);
        return "cart";
    }
    @RequestMapping("add-to-cart/{productId}")
    public RedirectView addToCart(@PathVariable(value = "productId") Long idProduit) {
        createCartIfNoCart();
        msCartProxy.addProductToCart(msCartProxy.getCartId(),new CartItemBean(idProduit,1));
        return new RedirectView("/cart");
    }
    @RequestMapping("remove-from-cart/{itemId}")
    public ModelAndView deleteFromCart(@PathVariable(value = "itemId") long itemId) {
        createCartIfNoCart();
        Optional<CartBean> cart = msCartProxy.getCart(1L);
        if(cart.isPresent()){
            CartBean cartBean = cart.get();
            int deleteIndice=0;
            List<CartItemBean> items = cart.get().getProducts();
            for(int i = 0;i<items.size();i++){
                if(items.get(i).getId()==itemId) deleteIndice = i;
            }
            try {
                cartBean.removeProduct(deleteIndice);
            } catch (Exception e){
                ModelAndView mv = new ModelAndView();
                mv.addObject("exception","Le produit n'est pas dans le panier, il ne peut pas être supprimé!");
                mv.setViewName("error");
                return mv;
            }
            msCartProxy.updateCart(cartBean);
        }
        return new ModelAndView(new RedirectView("/cart"));
    }
    @RequestMapping("commander")
    public ModelAndView createOrder() {
        //Holder for both Model and View in the web MVC framework
        ModelAndView mv = new ModelAndView();
        createCartIfNoCart();
        Optional<CartBean> optionalCart = msCartProxy.getCart(1L);
        List<CartItemBean> items = optionalCart.get().getProducts();
        if(items.isEmpty()){
            mv.addObject("exception","pas d'objet dans le panier. Commande non créée");
            mv.setViewName("error");
            return mv;
        }
        List<ProductBean> products = msProductProxy.list();
        CartBean cartBean = optionalCart.get();
        try {
            OrderBean orderBean = new OrderBean();
            for(CartItemBean cib : items){
                OrderItemBean oib = new OrderItemBean(cib.getId(), cib.getProductId(), cib.getQuantity(), cib.getTotalPrice(products));
                orderBean.addProduct(oib);
            }
            msOrderProxy.createNewOrder(orderBean);
            msCartProxy.updateCart(cartBean);
            msCartProxy.clearCart(1L);
        } catch (Exception e) {
            mv.addObject("exception",e);
            mv.setViewName("error");
            return mv;
        }
        RedirectView rv = new RedirectView("/orders");
        return new ModelAndView(rv);
    }
    @RequestMapping("orders")
    public String getOrders(Model model) {
        model.addAttribute("orders", msOrderProxy.list());
        model.addAttribute("products", msProductProxy.list());
        return "orders";
    }
}
