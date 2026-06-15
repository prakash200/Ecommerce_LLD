package models;

import enums.UserType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private List<Address> address;
    private Cart cart;
    private List<Order> orderHistory;
    private UserType userType;


    public User(int userId, String name, String email,String password, UserType userType){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.address = new ArrayList<>();
        this.cart = new Cart();
        this.orderHistory = new ArrayList<>();
    }

    public Cart getCart() {
        return cart;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Address> getAddress() {
        return address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void addOrder(Order order){
        this.orderHistory.add(order);
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

}
