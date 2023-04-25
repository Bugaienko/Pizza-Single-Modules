package balu.pizzarest.pizzaproject.dto.responsesModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * InlineResponse2001
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-18T20:07:26.808629200Z[GMT]")


public class UpdateUserResponse200 {
  @JsonProperty("jwt-token")
  private String jwtToken = null;

  @JsonProperty("updated data user")
  private String updatedDataUser = null;

  public UpdateUserResponse200 jwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
    return this;
  }

  /**
   * Get jwtToken
   * @return jwtToken
   **/
  @Schema(example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLGHpc3MiOiJQaXp6YSBhcHAiLCJleHAiOjE2ODE2MzU5NzgsImlhdCI6MTY4MTYzMjM3OCwidXNlcm5hbWUiOiJ0ZXN0In0.XvbElw5HT59q8aEPIEOm7wgbuIMou3_WfcogK4gJ__I", description = "")
  
    public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public UpdateUserResponse200 updatedDataUser(String updatedDataUser) {
    this.updatedDataUser = updatedDataUser;
    return this;
  }

  /**
   * Get updatedDataUser
   * @return updatedDataUser
   **/
  @Schema(example = "newUserName : newUserEmail", description = "")
  
    public String getUpdatedDataUser() {
    return updatedDataUser;
  }

  public void setUpdatedDataUser(String updatedDataUser) {
    this.updatedDataUser = updatedDataUser;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateUserResponse200 inlineResponse2001 = (UpdateUserResponse200) o;
    return Objects.equals(this.jwtToken, inlineResponse2001.jwtToken) &&
        Objects.equals(this.updatedDataUser, inlineResponse2001.updatedDataUser);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jwtToken, updatedDataUser);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2001 {\n");
    
    sb.append("    jwtToken: ").append(toIndentedString(jwtToken)).append("\n");
    sb.append("    updatedDataUser: ").append(toIndentedString(updatedDataUser)).append("\n");
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
