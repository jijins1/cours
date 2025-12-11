package ovh.ruokki.example.badpractices.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;  // Knows about buyer

    @OneToOne
    private ShoppingCart shoppingCart;  // Directly references cart

    private double totalAmount;
    private String status;
    private Date createdAt;

    public Order() {
        this.createdAt = new Date();
    }

    public Order(User user, ShoppingCart shoppingCart) {
        this.user = user;
        this.shoppingCart = shoppingCart;
        this.totalAmount = shoppingCart.calculateTotal();  // Couples calculation logic
        this.createdAt = new Date();
        this.status = "PENDING";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public ShoppingCart getShoppingCart() { return shoppingCart; }
    public void setShoppingCart(ShoppingCart shoppingCart) { this.shoppingCart = shoppingCart; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public void processPayment() {
        if ("PENDING".equals(status)) {
            this.status = "PROCESSING";
            System.out.println("Processing payment for order " + id);
        }
    }
}
