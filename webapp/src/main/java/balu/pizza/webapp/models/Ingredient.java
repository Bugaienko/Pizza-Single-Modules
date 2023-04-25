package balu.pizza.webapp.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Name should be not empty")
    private String name;
    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeIngredient type;
    @Column(name = "image")
    private String image;

    @ManyToMany(mappedBy = "ingredients")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Pizza> pizzas;

    public Ingredient() {
    }

    public Ingredient(String name, Double price) {
        this.name = name;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }





    public void setPrice(Double price) {
        this.price = price;
    }

    public TypeIngredient getType() {
        return type;
    }

    public void setType(TypeIngredient type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
//                ", type=" + type.getName() +
                ", image='" + image + '\'' +
                '}';
    }
}
