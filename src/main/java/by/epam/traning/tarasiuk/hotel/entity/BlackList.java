package by.epam.traning.tarasiuk.hotel.entity;

import java.util.Objects;

public class BlackList {
    private int id;
    private int client;
    private String reason;

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlackList blackList = (BlackList) o;
        return id == blackList.id &&
                client == blackList.client;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client);
    }

    @Override
    public String toString() {
        return "BlackList{" +
                "black_list_id=" + id +
                ", client=" + client +
                ", reason='" + reason + '\'' +
                '}';
    }
}
