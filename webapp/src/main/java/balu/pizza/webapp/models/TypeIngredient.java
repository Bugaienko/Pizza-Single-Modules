package balu.pizza.webapp.models;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeIngredient that = (TypeIngredient) o;

        if (id != that.id) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
