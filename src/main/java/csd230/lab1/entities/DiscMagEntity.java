package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "discmags")
public class DiscMagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private double price;

    private int copies;

    private int orderQty;

    // Extra field to represent “disc magazine” concept
    private String discType; // e.g., DVD, CD, BluRay

    public DiscMagEntity() {}

    public DiscMagEntity(String title, double price, int copies, int orderQty, String discType) {
        this.title = title;
        this.price = price;
        this.copies = copies;
        this.orderQty = orderQty;
        this.discType = discType;
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }

    public int getOrderQty() { return orderQty; }
    public void setOrderQty(int orderQty) { this.orderQty = orderQty; }

    public String getDiscType() { return discType; }
    public void setDiscType(String discType) { this.discType = discType; }
}
