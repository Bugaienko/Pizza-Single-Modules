package balu.pizzarest.pizzaproject.dto.responsesModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * InlineResponse200
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-18T20:07:26.808629200Z[GMT]")


public class JwtTokenResponse200 {
  @JsonProperty("jwt-token")
  private String jwtToken = null;

  public JwtTokenResponse200 jwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
    return this;
  }

  /**
   * Get jwtToken
   * @return jwtToken
   **/
  @Schema(description = "")
  
    public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JwtTokenResponse200 inlineResponse200 = (JwtTokenResponse200) o;
    return Objects.equals(this.jwtToken, inlineResponse200.jwtToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jwtToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
    sb.append("    jwtToken: ").append(toIndentedString(jwtToken)).append("\n");
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
