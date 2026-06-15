# Ecommerce System - Low Level Design

## Overview
A comprehensive low-level design of an Ecommerce system (customer-facing) built using Java. This project implements core ecommerce functionalities including user management, product search, cart operations, order placement, payment processing, and order tracking. The design leverages three key design patterns: **Singleton**, **Strategy**, and **State**.

---

## Design Patterns Used

### 1. Singleton Pattern - Amazon System
- The **Amazon** class itself is a singleton, accessed via `Amazon.getAmazonInstance()`.
- A single instance manages the entire system — users, products, cart, order, and payment services.
- Ensures a centralized state (product catalog, user registry) across the application.

### 2. Strategy Pattern - Payment Types
- **`PaymentStrategy`** interface defines a common `pay(Double amount)` contract.
- Concrete implementations: **`UPIStrategy`**, **`DebitCard`**, **`CreditCard`**.
- The system can dynamically select the appropriate payment method at runtime without modifying existing code.

### 3. State Pattern - Order Status Tracking ( Future Scope )
- **`OrderState`** interface defines state-specific behaviors: `ship()`, `deliver()`, `cancel()`.
- Concrete states: **`OrderPlacedState`**, **`ShippedState`**, **`DeliveredState`**, **`CancelledState`**.
- Each `Order` object transitions through valid states, encapsulating behavior and preventing invalid transitions.

---

## Entities (Classes)

### User
| Field | Type | Description |
|-------|------|-------------|
| userId | int | Unique identifier |
| name | String | User's full name |
| email | String | Email address (used for login) |
| password | String | Account password |
| address | List\<Address\> | List of user's addresses |
| userType | UserType | Enum: CUSTOMER, SELLER, ADMIN |
| cart | Cart | User's shopping cart |
| orderHistory | List\<Order\> | Past orders placed by the user |

### Address
| Field | Type | Description |
|-------|------|-------------|
| street | String | Street address |
| city | String | City |
| state | String | State |
| country | String | Country |

### Product
| Field | Type | Description |
|-------|------|-------------|
| prodId | int | Unique product ID |
| name | String | Product name |
| desc | String | Product description |
| category | ProductCategory | Enum: ELECTRONICS, CLOTHING, BOOKS, etc. |
| mrp | Double | Maximum retail price |
| price | Double | Selling price |
| stock | int | Available stock quantity |
| reviews | List\<Review\> | Customer reviews |

### Cart
| Field | Type | Description |
|-------|------|-------------|
| cartId | int | Unique cart ID |
| cartItems | Map\<Integer, CartItem\> | Items in cart (keyed by product ID) |

**Methods:**
- `void addItem(Product product, int quantity)` — Add product to cart
- `void removeItem(Product product)` — Remove product from cart
- `Double calculateTotal()` — Calculate cart total
- `void clearCart()` — Clear all items from cart
- `List<CartItem> getItems()` — Get all cart items

### CartItem
| Field | Type | Description |
|-------|------|-------------|
| cartItemId | int | Unique cart item ID |
| product | Product | Associated product |
| quantity | int | Quantity of the product |

**Methods:**
- `void computeTotal()` — Calculate total price (price × quantity)

### Order
| Field | Type | Description |
|-------|------|-------------|
| orderId | int | Unique order ID |
| user | User | User who placed the order |
| address | Address | Delivery address |
| orderedDate | DateTime | Order placement timestamp |
| totalAmount | Double | Total order amount |
| orderItems | List\<OrderItem\> | Items in the order |
| currentState | OrderState | Current order state (State pattern) |
| payment | Payment | Payment details |

**State Behaviors:**
- `void ordered()` — Transition to placed state
- `void ship()` — Transition to shipped state
- `void deliver()` — Transition to delivered state
- `void cancel()` — Transition to cancelled state

### OrderItem
| Field | Type | Description |
|-------|------|-------------|
| productName | String | Product name (snapshotted at order time) |
| category | ProductCategory | Product category (snapshotted at order time) |
| quantity | int | Quantity ordered |
| price | Double | Price at time of order |
| product | Product | Reference to the actual product |

> **Note:** OrderItem inherits/shadow product details so that changes to the product catalog in the future do not alter historical order records.

### Review
| Field | Type | Description |
|-------|------|-------------|
| totalStars | int | Rating (1–5) |
| description | String | Review text |
| createdBy | User | User who submitted the review |

### Payment
| Field | Type | Description |
|-------|------|-------------|
| amount | Double | Payment amount |
| paymentStatus | PaymentStatus | Enum: PENDING, COMPLETED, FAILED, REFUNDED |
| date | DateTime | Payment timestamp |

---

## Enums

### UserType
- `CUSTOMER`
- `SELLER`
- `ADMIN`

> For roles with limited dedicated fields, an enum suffices. For roles requiring extended attributes, inherit from the `User` class.

### OrderStatus / OrderState
- `ORDER_PLACED`
- `SHIPPED`
- `DELIVERED`
- `CANCELLED`

### ProductCategory
- `ELECTRONICS`
- `CLOTHING`
- `FOOTWEAR`
- `BOOKS`
- `HOME`
- `BEAUTY`
- `SPORTS`
- `TOYS`
- `GROCERY`
- (expandable as needed)

### PaymentStatus
- `PENDING`
- `COMPLETED`
- `FAILED`
- `REFUNDED`

---

## Interfaces

### PaymentStrategy
```java
public interface PaymentStrategy {
    void pay(Double amount);
}
```

**Implementations:**
- `UpiPaymentStrategy`
- `DebitCardPaymentStrategy`
- `CreditCardPaymentStrategy`

### OrderState
```java
public interface OrderState {
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
}
```

**Implementations:**
- `OrderPlacedState`
- `ShippedState`
- `DeliveredState`
- `CancelledState`

---

## Services

### InventoryService (Singleton)
Manages the product catalog. Acts as a central product repository.

| Method | Description |
|--------|-------------|
| `List<Product> getProductByName(String name)` | Search products by name |
| `List<Product> getProductByCategory(ProductCategory category)` | Filter products by category |

### UserService
Manages user registration and profile operations.

| Method | Description |
|--------|-------------|
| `void addUser(int userId, User user)` | Register a new user |
| `User getUser(int userId)` | Retrieve user by ID |
| `void updateUser(int userId, User updatedUser)` | Update user profile |
| `void deleteUser(int userId)` | Delete user account |

### CartService
Handles add/remove operations on the user's cart.

| Method | Description |
|--------|-------------|
| `void addItem(User user, Product product, int quantity)` | Add product to user's cart |
| `void removeItem(User user, Product product)` | Remove product from user's cart |

### OrderService
Handles order placement logic. Reduces inventory on order placement.

| Method | Description |
|--------|-------------|
| `void placeOrder(User user)` | Convert cart items to order, process payment, update inventory |

### SearchService
Provides product search capabilities across the inventory.

| Method | Description |
|--------|-------------|
| `List<Product> searchByName(String name)` | Search by name |
| `List<Product> searchByCategory(ProductCategory category)` | Search by category |

---

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────────┐
│                           Amazon System                             │
├─────────────────────────────────────────────────────────────────────┤
│  ┌──────────┐    ┌───────────┐    ┌───────────┐    ┌───────────┐  │
│  │  User    │    │ Address   │    │  Product  │    │   Cart    │  │
│  ├──────────┤    ├───────────┤    ├───────────┤    ├───────────┤  │
│  │- userId  │    │- street   │    │- prodId   │    │- cartId   │  │
│  │- name    │    │- city     │    │- name     │    │- cartItems│  │
│  │- email   │◄───│- state    │    │- desc     │    ├───────────┤  │
│  │- password│    │- country  │    │- category │    │+ addItem()│  │
│  │- address │    └───────────┘    │- mrp      │    │+ remove() │  │
│  │- userType│                     │- price    │    │+ calcTotl │  │
│  │- cart    │                     │- stock    │    │+ clear()  │  │
│  │- orders  │                     │- reviews  │    │+ getItems │  │
│  └──────────┘                     └───────────┘    └───────────┘  │
│                                        ▲                 ▲        │
│                                        │                 │        │
│                                  ┌─────┴─────┐   ┌──────┴──────┐ │
│                                  │  Review   │   │  CartItem   │ │
│                                  ├───────────┤   ├─────────────┤ │
│                                  │- totalStars│  │- cartItemId │ │
│                                  │- desc     │   │- product    │ │
│                                  │- createdBy│   │- quantity   │ │
│                                  └───────────┘   │+ computeTot │ │
│                                                  └─────────────┘ │
│  ┌──────────┐    ┌───────────┐    ┌───────────┐                  │
│  │  Order   │    │ OrderItem │    │  Payment  │                  │
│  ├──────────┤    ├───────────┤    ├───────────┤                  │
│  │- orderId │    │- productNm│    │- amount   │                  │
│  │- user    │    │- category │    │- status   │                  │
│  │- address │◄───│- quantity │    │- date     │                  │
│  │- date    │    │- price    │    └───────────┘                  │
│  │- totalAmt│    │- product  │                                   │
│  │- items   │    └───────────┘                                   │
│  │- state   │                                                    │
│  │- payment │                                                    │
│  └──────────┘                                                    │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│                        Strategy Pattern                             │
├─────────────────────────────────────────────────────────────────────┤
│                     «Interface» PaymentStrategy                     │
│                     ──────────────────────────                      │
│                     + pay(Double amount)                            │
│                           ▲                                         │
│         ┌─────────────────┼─────────────────┐                      │
│         │                 │                 │                       │
│ ┌───────┴───────┐ ┌──────┴───────┐ ┌───────┴───────┐              │
│ │  UPIPayment   │ │DebitCardPaymt│ │CreditCardPaymt│              │
│ └───────────────┘ └──────────────┘ └───────────────┘              │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│                          State Pattern                              │
├─────────────────────────────────────────────────────────────────────┤
│                     «Interface» OrderState                          │
│                     ────────────────────────                        │
│                     + ship(Order order)                             │
│                     + deliver(Order order)                          │
│                     + cancel(Order order)                           │
│                           ▲                                         │
│         ┌─────────────────┼──────────────────┐                     │
│         │                 │                  │                      │
│ ┌───────┴────────┐ ┌─────┴────────┐ ┌───────┴────────┐           │
│ │OrderPlacedState│ │ShippedState  │ │DeliveredState  │           │
│ └────────────────┘ └──────────────┘ └────────────────┘           │
│                                                     ┌────────────┐│
│                                                     │CancelledSt ││
│                                                     └────────────┘│
└─────────────────────────────────────────────────────────────────────┘
```

---

## Key Design Decisions

1. **Customer-Facing System** — This design focuses on the customer side. A seller-facing system would introduce dedicated seller classes, attributes, and capabilities.

2. **Object References over IDs (Java)** — In memory (Java objects), we store class instance references instead of IDs. For database schemas, foreign keys (IDs) are used.

3. **OrderItem Snapshot** — `OrderItem` duplicates product details (`productName`, `category`, `price`) so that historical order data remains accurate even if product catalog information changes later.

4. **UserType Handling** — An `enum` is sufficient when role-specific fields are minimal. For richer role-specific behaviors and attributes, subclassing `User` into `Customer`, `Seller`, `Admin` is recommended.

5. **Cart ↔ Order Similarity** — Both `Cart` and `Order` manage a collection of items. The distinction is that `Cart` is mutable and transient, while `Order` is immutable (once placed) and persisted.

---

## Flow Summary

```
User Signup/Login
       │
       ▼
Browse/Search Products (via SearchService / InventoryService)
       │
       ▼
Add Products to Cart (CartService)
       │
       ▼
Place Order (OrderService → reduces stock, creates Order)
       │
       ▼
Make Payment (Strategy: UPI / DebitCard / CreditCard)
       │
       ▼
Track Order Status (State: Placed → Shipped → Delivered / Cancelled)
```

---

## Sample Terminal Output

```
Welcome to Amazon

All Products : {1=Product{prodId=1, name='Mac Book Air', category=Electronics, price=125.0, stock=20},
                2=Product{prodId=2, name='Iphone 15', category=Electronics, price=75.0, stock=25},
                3=Product{prodId=3, name='Polo T-shirts', category=Clothing, price=30.0, stock=30}}

Products list based on name: [Product{prodId=1, name='Mac Book Air', category=Electronics, price=125.0, stock=20}]
Products list based on category: [Product{prodId=1, name='Mac Book Air', category=Electronics, price=125.0, stock=20},
                                   Product{prodId=2, name='Iphone 15', category=Electronics, price=75.0, stock=25}]

Processing payment of Rs. 1325.0 through UPI
Payment Successful of Rs. 1325.0

Order of [OrderItem{productName='Mac Book Air', quantity=10, price=125.0},
          OrderItem{productName='Iphone 15', quantity=1, price=75.0}] placed by Prakash

Remaining quantity of products : {1=Product{prodId=1, name='Mac Book Air', category=Electronics, price=125.0, stock=10},
                                  2=Product{prodId=2, name='Iphone 15', category=Electronics, price=75.0, stock=24},
                                  3=Product{prodId=3, name='Polo T-shirts', category=Clothing, price=30.0, stock=30}}

User Order History : [Order{orderId=1, user=Prakash, totalAmount=1325.0, items=2, state=ORDER_PLACED}]
```

---

## Future Scope

- **State Pattern Implementation** — Complete the `OrderState` transitions (ShippedState, DeliveredState, CancelledState) for full order lifecycle tracking.
- **Additional Payment Strategies** — Add `DebitCardPaymentStrategy` and `CreditCardPaymentStrategy` implementations.
- **Database Persistence** — Replace in-memory storage with MySQL/PostgreSQL using JDBC or Hibernate.
- **REST API** — Expose services as REST endpoints using Spring Boot.
- **Authentication** — Add JWT-based login/signup with password hashing.
- **Unit Tests** — Add JUnit test coverage for all services and edge cases.

---

## Getting Started

### Prerequisites
- Java 8+
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code)

### Run the Demo
```bash
# Compile all Java files
javac -d out src/**/*.java

# Run the main demo class
java -cp out AmazonDemo
```

---

## Project Structure
```
src/
├── Amazon.java               # Main system orchestrator
├── AmazonDemo.java           # Demo runner
├── enums/
│   ├── OrderStatus.java
│   ├── PaymentStatus.java
│   ├── ProductCategory.java
│   └── UserType.java
├── exceptions/
│   └── OutOfStockException.java
├── models/
│   ├── Address.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Payment.java
│   ├── Product.java
│   ├── Review.java
│   └── User.java
├── services/
│   ├── CartService.java
│   ├── OrderService.java
│   └── SearchService.java
└── strategy/
    ├── PaymentStrategy.java
    └── UpiPaymentStrategy.java