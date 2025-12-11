package ovh.ruokki.example.badpractices.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;  // Knows about owner

    @ManyToMany
    private List<Product> products;  // Has many products

    @OneToOne(mappedBy = "shoppingCart")
    private Order order;  // Knows about orders created from it

    public ShoppingCart() {}

    public ShoppingCart(User user) {
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public double calculateTotal() {
        if (products == null) return 0;
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
