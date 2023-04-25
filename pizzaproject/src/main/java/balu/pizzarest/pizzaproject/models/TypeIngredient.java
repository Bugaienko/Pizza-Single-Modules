package balu.pizzarest.pizzaproject.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Entity
@Table(name = "type")
public class TypeIngredient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Name should be not empty")
    private String name;

    @OneToMany(mappedBy = "type")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Ingredient> ingredients;

    public TypeIngredient() {
    }

    public TypeIngredient(String name) {
        this.name = name;
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


    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }



    @Override
    public String toString() {
        return "TypeIngredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
