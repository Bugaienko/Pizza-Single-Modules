package balu.pizza.webapp.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Entity
@Table(name = "base")
public class Base {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "size")
    @NotEmpty(message = "Size should be not empty")
    private String size;
    @Column(name = "name")
    @NotEmpty(message = "Name should be not empty")
    private String name;
    @Column(name = "price")
    private double price;


    @OneToMany(mappedBy = "base")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Pizza> pizzas;

    public Base() {
    }


    public Base(String size, String name, double price) {
        this.size = size;
        this.name = name;
        this.price = price;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                ", size='" + size + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
