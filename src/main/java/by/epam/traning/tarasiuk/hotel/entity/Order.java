package by.epam.traning.tarasiuk.hotel.entity;

import java.sql.Date;
import java.util.Objects;

public class Order {
    private int id;
    private int client;
    private int room;
    private Date firstday;
    private Date lastday;

    public Order() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public Date getFirstday() {
        return firstday;
    }

    public void setFirstday(Date firstday) {
        this.firstday = firstday;
    }

    public Date getLastday() {
        return lastday;
    }

    public void setLastday(Date lastday) {
        this.lastday = lastday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                client == order.client &&
                Objects.equals(firstday, order.firstday) &&
                Objects.equals(lastday, order.lastday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, firstday, lastday);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", firstday=" + firstday +
                ", lastday=" + lastday +
                '}';
    }
}
