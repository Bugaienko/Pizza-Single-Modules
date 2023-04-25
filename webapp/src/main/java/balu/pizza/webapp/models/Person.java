package balu.pizza.webapp.models;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @Pattern(regexp = "([a-zA-Z]+[\\w\\s]*){3,}", message = "The username must contain only letters, numbers, underscores. Not shorter than 3 characters.")
    private String username;
    @Column(name = "password")
    @NotEmpty(message = "Password should be not empty")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    private String password;
    @Column(name = "email")
    @Email
    @NotEmpty(message = "Email should be not empty")
    private String email;
    @Column(name = "role")
    private String role;
    @Column(name = "avatar")
    private String avatar;

    @ManyToMany
    @JoinTable(
            name = "person_pizza",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<Pizza> favorites;

    public Person() {
    }

    public Person(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Person(String username, String password, String email, String avatar) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
    }



    public List<Pizza> getSortedPizza(){
        List<Pizza> pizzas = this.favorites;
        pizzas.sort(((o1, o2) -> {
            return (int) (o1.getPrice() - o2.getPrice());
        }));
        return pizzas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Pizza> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Pizza> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

//    public static List<Pizza> deleteDublicates(List<Pizza> pizzas) {
//        Set<Pizza> set = new LinkedHashSet<>(pizzas);
//        return new ArrayList<>(set);
//    }
}
