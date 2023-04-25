package balu.pizzarest.pizzaproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Objects;

/**
 * BodyPizza
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-18T20:07:26.808629200Z[GMT]")


public class BodyAddPizza {
  @JsonProperty("name")
  @NotNull
  private String name = null;

  @JsonProperty("price")
  private double price = 0;

  @JsonProperty("base_id")
  @NotNull
  private Integer baseId = null;

  @JsonProperty("image")
  private String image = null;

  public BodyAddPizza name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(example = "Margarita S", required = true, description = "")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BodyAddPizza price(double price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
   **/
  @Schema(example = "25.5", required = true, description = "")
      @NotNull

    @Valid
    public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public BodyAddPizza baseId(Integer baseId) {
    this.baseId = baseId;
    return this;
  }

  /**
   * Get baseId
   * @return baseId
   **/
  @Schema(example = "2", required = true, description = "")
      @NotNull

    public Integer getBaseId() {
    return baseId;
  }

  public void setBaseId(Integer baseId) {
    this.baseId = baseId;
  }

  public BodyAddPizza image(String image) {
    this.image = image;
    return this;
  }

  /**
   * Get image
   * @return image
   **/
  @Schema(example = "p_margarita.png", description = "")
  
    public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BodyAddPizza bodyAddPizza = (BodyAddPizza) o;
    return Objects.equals(this.name, bodyAddPizza.name) &&
        Objects.equals(this.price, bodyAddPizza.price) &&
        Objects.equals(this.baseId, bodyAddPizza.baseId) &&
        Objects.equals(this.image, bodyAddPizza.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, price, baseId, image);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BodyPizza {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    baseId: ").append(toIndentedString(baseId)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
