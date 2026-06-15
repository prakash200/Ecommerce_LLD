import enums.ProductCategory;
import enums.UserType;
import models.Cart;
import models.Product;
import models.User;
import services.CartService;
import services.OrderService;
import services.SearchService;
import strategy.PaymentStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Amazon {
    private static Amazon amazonInstance;

    public final Map<Integer, Product> products;
    public final Map<Integer, User> users;

    public final CartService cartService;
    public final OrderService orderService;
    public final SearchService searchService;

    public PaymentStrategy paymentStrategy;


    private Amazon(){
        this.users = new HashMap<>();
        this.products = new HashMap<>();

        this.cartService = new CartService();
        this.orderService = new OrderService();
        this.searchService = new SearchService(products);
    }

    public static synchronized Amazon getAmazonInstance(){
        if (amazonInstance==null){
            amazonInstance = new Amazon();
        }
        return amazonInstance;
    }


    public User addUser(int userId, String name, String email,String password, UserType userType){
        User user = new User(userId, name, email, password, userType);
        users.put(user.getUserId(), user);
        return user;
    }

    public Product addProduct(int productId, String name, String description, ProductCategory category, Double mrp, Double price, int quantity){
        Product product = new Product(productId, name, description, category, mrp,price, quantity);
        products.put(productId, product);
        return product;
    }

    public void addItemToCart(User user, Product product, int quantity){

        try {
            cartService.addItems(user, product, quantity);
        }catch (Exception e){
            System.out.println("Error Occurred : " + e.getMessage());
        }
    }

    public void placeOrder(User user, Cart cart){
        try{
            orderService.placeOrder(user, cart, paymentStrategy);
        } catch (Exception e) {
            System.out.println("Error Occurred : " + e.getMessage());
        }

    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public List<Product> searchByName(String name){
        return searchService.searchByName(name);
    }

    public List<Product> searchByCategory(ProductCategory category){
        return searchService.searchByCategory(category);
    }



    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
}
