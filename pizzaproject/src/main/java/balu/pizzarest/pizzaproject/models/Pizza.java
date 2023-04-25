package balu.pizzarest.pizzaproject.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Entity
@Table(name = "pizza")
public class Pizza {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Name should be not empty")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "image")
    private String image;

    @ManyToMany
    @JoinTable(
            name = "pizza_ingredient",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;


    @ManyToMany(mappedBy = "pizzas")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Cafe> cafes;

    @ManyToOne
    @JoinColumn(name = "base_id", referencedColumnName = "id")
    private Base base;

    @ManyToMany(mappedBy = "favorites")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Person> persons;

    public Pizza() {
    }

    public Pizza(String name, double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Cafe> getCafes() {
        return cafes;
    }

    public void setCafes(List<Cafe> cafes) {
        this.cafes = cafes;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", base=" + base +
                '}';
    }
}
