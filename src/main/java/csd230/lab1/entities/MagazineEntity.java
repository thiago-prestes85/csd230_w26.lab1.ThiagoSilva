package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "magazines")
public class MagazineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private double price;

    private int copies;

    private int orderQty;

    public MagazineEntity() {}

    public MagazineEntity(String title, double price, int copies, int orderQty) {
        this.title = title;
        this.price = price;
        this.copies = copies;
        this.orderQty = orderQty;
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
}
