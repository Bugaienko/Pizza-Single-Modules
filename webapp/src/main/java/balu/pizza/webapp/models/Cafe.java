package balu.pizza.webapp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * The Entity of the cafe
 *
 * @author Sergii Bugaienko
 *
 */

@Entity
@Table(name = "cafe")
public class Cafe {

    /** Identifier */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Name of cafe */
    @Column(name = "title")
    @NotEmpty(message = "Title should be not empty")
    private String title;

    /** Full address */
    @Column(name = "city")
    @NotEmpty(message = "City should be not empty")
    private String city;

    /** Email of cafe */
    @Column(name = "email")
    @NotEmpty(message = "Email should be not empty")
    private String email;

    /** Phone of cafe */
    @Column(name = "phone")
    @NotEmpty(message = "Phone should be not empty")
    private String phone;

    /** Cafe opening time */
    @Column(name = "open_at")
    private String openAt;

    /** Cafe closing time */
    @Column(name = "close_at")
    private String closeAt;

    /** List of pizzas related to the cafe */
    @ManyToMany
    @JoinTable(
            name = "cafe_pizza",
            joinColumns = @JoinColumn(name = "cafe_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<Pizza> pizzas;

    /** Image file name */
    @Column(name = "image")
    private String image;

    /**
     * Empty class constructor
     */
    public Cafe() {
    }


    /**
     * Class constructor
     *
     * @param title Name of cafe
     * @param city Full address
     * @param email Email
     * @param phone Phone
     * @param openAt Cafe opening time
     * @param closeAt Cafe closing time
     */
    public Cafe(String title, String city, String email, String phone, String openAt, String closeAt) {
        this.title = title;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.openAt = openAt;
        this.closeAt = closeAt;
    }

    /**
     *
     * @return List sorted by price of pizzas related to the cafe
     */
    public List<Pizza> getSortedPizza(){
        List<Pizza> pizzas = this.pizzas;
        pizzas.sort(((o1, o2) -> {
            return (int) (o1.getPrice() - o2.getPrice());
        }));
        return pizzas;
    }


    /**
     *
     * @return Identity of cafe
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenAt() {
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
    }

    public String getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
    }

    /**
     *
     * @return List of pizzas related to the cafe
     */
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public String toString() {
        return "Cafe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", openAt=" + openAt +
                ", closeAt=" + closeAt +
                '}';
    }
}
