package models;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer, CartItem> cartItems;

    public Cart(){
        this.cartItems = new HashMap<>();
    }

    public void addItem(Product product, int quantity){
        if (cartItems.containsKey(product.getProductId())){
            CartItem cartItem = cartItems.get(product.getProductId());
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }else{
            cartItems.put(product.getProductId(), new CartItem(product, quantity));
        }
    }

    public Double calculateTotal(){
        double totalAmount = 0.0;
        for (int prodId: cartItems.keySet()){
            totalAmount+=cartItems.get(prodId).computeTotal();
        }

        return totalAmount;

    }


    public void removeItem(Product  product, int quantity){
        CartItem cartItem = cartItems.get(product.getProductId());
        cartItem.setQuantity(cartItem.getQuantity()-quantity);
    }

    public Map<Integer, CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart(){
        cartItems.clear();
    }


    @Override
    public String toString() {
        return "Cart{" +
                "cartItems=" + cartItems +
                '}';
    }
}
