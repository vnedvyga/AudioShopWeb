package ua.org.oa.nedvygav.data;


import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private final long id;
    private final LocalDateTime dateTime;
    private final List<Audio> items;
    private final int totalPrice;

    public Order(long id, LocalDateTime dateTime, List<Audio> items, int totalPrice) {
        this.id = id;
        this.dateTime=dateTime;
        this.items = items;
        this.totalPrice=totalPrice;
    }
    public Order(LocalDateTime dateTime, List<Audio> items, int totalPrice){
        this (0, dateTime, items, totalPrice);
    }

    public long getId() {
        return id;
    }

    public List<Audio> getItems() {
        return items;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
