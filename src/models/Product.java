package models;

import enums.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int productId;
    private String name;
    private String description;
    private ProductCategory category;
    private Double mrp;
    private Double price;
    private int stock;
    private List<Review> reviews;

    public Product(int productId, String name, String description, ProductCategory category, Double mrp, Double price, int stock){
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.mrp = mrp;
        this.price = price;
        this.stock = stock;
        this.reviews = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", stock=" + stock +
                '}';
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
