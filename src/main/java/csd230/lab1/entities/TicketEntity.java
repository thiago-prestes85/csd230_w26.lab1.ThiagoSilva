package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;

    private double price;

    private int quantity;

    public TicketEntity() {}

    public TicketEntity(String eventName, double price, int quantity) {
        this.eventName = eventName;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() { return id; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
