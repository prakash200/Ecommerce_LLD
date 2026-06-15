import enums.ProductCategory;
import enums.UserType;
import models.Product;
import models.User;
import strategy.UpiPaymentStrategy;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class AmazonDemo {
    public static void main(String[] args) {
        System.out.println("\nWelcome to Amazon\n");

        Amazon amazonSystem = Amazon.getAmazonInstance();

        User user1 = amazonSystem.addUser(1,"Prakash","prakash@gmail.com",
                "Prakash@123", UserType.Customer);

        Product laptops = amazonSystem.addProduct(1,"Mac Book Air", "laptop for smoother experience",
                ProductCategory.Electronics, 150.00, 125.00, 20);

        Product mobiles = amazonSystem.addProduct(2,"Iphone 15", "iphone for smoother experience",
                ProductCategory.Electronics, 100.00, 75.00, 25);

        Product tShirts = amazonSystem.addProduct(3,"Polo T-shirts ", "T-shirts for comfort",
                ProductCategory.Clothing, 50.00, 30.00, 30);

        System.out.println("\nAll Products : " + amazonSystem.getProducts());

        System.out.println("\nProducts list based on name: "+ amazonSystem.searchByName("Mac Book Air"));
        System.out.println("Products list based on category "+ amazonSystem.searchByCategory(ProductCategory.Electronics));

        amazonSystem.addItemToCart(user1, laptops, 10);
        amazonSystem.addItemToCart(user1 , mobiles, 1);

        amazonSystem.setPaymentStrategy(new UpiPaymentStrategy());
        amazonSystem.placeOrder(user1, user1.getCart());

        System.out.println("\nRemaining quantity of products : " + amazonSystem.getProducts());


       System.out.println("User Order History : " + user1.getOrderHistory());






    }
}