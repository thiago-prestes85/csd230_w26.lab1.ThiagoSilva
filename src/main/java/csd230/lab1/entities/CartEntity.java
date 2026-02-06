package csd230.lab1.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart_entity")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===============================
    // LAB 3
    // Cart <-> User (One-to-One)
    // ===============================
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // ===============================
    // LAB 3
    // Cart <-> Books (Many-to-Many)
    // ===============================
    @ManyToMany
    @JoinTable(
            name = "cart_books",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<BookEntity> products = new HashSet<>();
    // ↑ mantive o nome "products" para não quebrar o HTML

    // ===============================
    // Constructors
    // ===============================
    public CartEntity() {}

    // ===============================
    // Getters and Setters
    // ===============================
    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<BookEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<BookEntity> products) {
        this.products = products;
    }

    // ===============================
    // Helper method
    // ===============================
    public void addProduct(BookEntity book) {
        this.products.add(book);
    }
}
