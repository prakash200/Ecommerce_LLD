package services;

import exceptions.OutOfStockException;
import models.*;
import strategy.PaymentStrategy;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    public void placeOrder(User user, Cart cart, PaymentStrategy strategy){
        List<OrderItem> orderItems = new ArrayList<>();

        for (int prodId : cart.getCartItems().keySet()){
            CartItem cartItem = cart.getCartItems().get(prodId);
            if (cartItem.getQuantity()>cartItem.getProduct().getStock()){
                throw new OutOfStockException("Not enough Stock for "+ cartItem.getProduct().getName() +" to place the order" );
            }
            orderItems.add(new OrderItem(cartItem.getProduct(), cartItem.getQuantity()));

            // decrease the stock
            Product product = cartItem.getProduct();
            product.setStock(product.getStock()- cartItem.getQuantity());
        }
        Address address = new Address("Street 123", "Gurugram", "Haryana","India");
        Double amount = cart.calculateTotal();

        Payment payment = strategy.pay(amount);
        Order order = new Order(user, orderItems, address, amount, payment);

        user.addOrder(order);
        cart.clearCart();

        System.out.println("\n\nOrder of " + order.getOrderItems() + " placed by " + user.getName());
    }
}
