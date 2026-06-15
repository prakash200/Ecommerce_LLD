package models;

import enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Order {


    private User user;
    private LocalDateTime orderedDate;
    private List<OrderItem> orderItems;
    private Address address;
    private Double totalAmount;
    private Payment payment;
    private OrderStatus orderStatus;

    public Order ( User user, List<OrderItem> items, Address address, Double totalAmount, Payment payment){

        this.user = user;
        this.orderItems = items;
        this.orderedDate = LocalDateTime.now();
        this.address = address;
        this.totalAmount = totalAmount;
        this.payment = payment;
        this.orderStatus = OrderStatus.Order_Placed;

    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", orderedDate=" + orderedDate +
                ", orderItems=" + orderItems +
                ", address=" + address +
                ", totalAmount=" + totalAmount +
                ", payment=" + payment +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
