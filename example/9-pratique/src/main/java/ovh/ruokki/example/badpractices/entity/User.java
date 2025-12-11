package ovh.ruokki.example.badpractices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;

    @OneToOne
    private ShoppingCart shoppingCart;  // Tight coupling: User owns shopping cart

    @OneToOne(mappedBy = "user")
    private Order lastOrder;  // Knows about user's orders

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public ShoppingCart getShoppingCart() { return shoppingCart; }
    public void setShoppingCart(ShoppingCart shoppingCart) { this.shoppingCart = shoppingCart; }

    public Order getLastOrder() { return lastOrder; }
    public void setLastOrder(Order lastOrder) { this.lastOrder = lastOrder; }
}
