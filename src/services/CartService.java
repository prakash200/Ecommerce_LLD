package services;

import exceptions.OutOfStockException;
import models.Cart;
import models.Product;
import models.User;

public class CartService {

    public void addItems(User user, Product product, int quantity){

        Cart userCart = user.getCart();
        if (product.getStock()>=quantity){
            userCart.addItem(product, quantity);
            System.out.printf("\n%s of quantity %d has been added to cart of %s", product.getName(), quantity, user.getName());
        }else{
            throw new OutOfStockException("Not enough stock for "+product.getName()+" to add to cart");
        }



    }
}
