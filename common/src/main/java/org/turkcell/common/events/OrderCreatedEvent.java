package org.turkcell.common.events;

import java.time.LocalDateTime;

public class OrderCreatedEvent {

    private int id;
    private LocalDateTime date;

    public OrderCreatedEvent(int id, LocalDateTime date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
