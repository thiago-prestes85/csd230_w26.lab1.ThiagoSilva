package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    private double pubPrice;

    private int copies;

    public BookEntity() {}

    public BookEntity(String title, String author, double pubPrice, int copies) {
        this.title = title;
        this.author = author;
        this.pubPrice = pubPrice;
        this.copies = copies;
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public double getPubPrice() { return pubPrice; }
    public void setPubPrice(double pubPrice) { this.pubPrice = pubPrice; }

    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }

    @Override
    public String toString() {
        return "BookEntity{id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pubPrice=" + pubPrice +
                ", copies=" + copies +
                '}';
    }
}
