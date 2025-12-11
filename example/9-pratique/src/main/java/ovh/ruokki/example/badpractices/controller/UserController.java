package ovh.ruokki.example.badpractices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ovh.ruokki.example.badpractices.entity.Order;
import ovh.ruokki.example.badpractices.entity.ShoppingCart;
import ovh.ruokki.example.badpractices.entity.User;
import ovh.ruokki.example.badpractices.repository.OrderRepository;
import ovh.ruokki.example.badpractices.repository.ShoppingCartRepository;
import ovh.ruokki.example.badpractices.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;

    public UserController(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        ShoppingCart cart = new ShoppingCart(user);
        user.setShoppingCart(cart);

        shoppingCartRepository.save(cart);
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/{userId}/orders")
    public Order createOrder(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        ShoppingCart cart = user.getShoppingCart();
        Order order = new Order(user, cart);

        // Coupling: User knows about orders
        user.setLastOrder(order);
        cart.setOrder(order);

        orderRepository.save(order);
        userRepository.save(user);
        shoppingCartRepository.save(cart);

        return order;
    }

    @GetMapping("/{userId}/cart/total")
    public double getCartTotal(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return user.getShoppingCart().calculateTotal();
    }
}
