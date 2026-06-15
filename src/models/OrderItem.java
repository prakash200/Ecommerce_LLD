package models;

import enums.ProductCategory;

public class OrderItem {

    private final Product product;
    private final String productName;
    private final ProductCategory category;
    private final int quantity;
    private final Double price;

    @Override
    public String toString() {
        return "OrderItem{" +
                "productName='" + productName + '\'' +
                ", category=" + category +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public OrderItem(Product product, int quantity){
        this.product = product;
        this.productName = product.getName();
        this.category = product.getCategory();
        this.quantity = quantity;
        this.price = product.getPrice();
    }

}
