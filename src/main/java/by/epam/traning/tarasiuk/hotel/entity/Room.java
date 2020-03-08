package by.epam.traning.tarasiuk.hotel.entity;

import java.util.Objects;

public class Room {
    private int id;
    private String placement;
    private String comfort;
    private int person;
    private String convenience;
    private String description;
    private double price;
    private String path;

    public Room() {

    }

    public Room(int id, String placement, String comfort, String description, double price) {
        this.id = id;
        this.placement = placement;
        this.comfort = comfort;
        this.description = description;
        this.price = price;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getConvenience() {
        return convenience;
    }

    public void setConvenience(String convenience) {
        this.convenience = convenience;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getComfort() {
        return comfort;
    }

    public void setComfort(String comfort) {
        this.comfort = comfort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Double.compare(room.price, price) == 0 &&
                placement.equals(room.placement) &&
                comfort.equals(room.comfort) &&
                description.equals(room.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placement, comfort, convenience, description, price);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@{" + "number = " + id +
                ", placement = " + placement + ", comfort = " + comfort +
                ", convenience=" + convenience + ", description = " + description +
                ", price = " + price + '}';
    }
}
