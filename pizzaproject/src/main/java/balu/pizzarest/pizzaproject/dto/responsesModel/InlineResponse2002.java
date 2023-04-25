package balu.pizzarest.pizzaproject.dto.responsesModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.util.Objects;

/**
 * InlineResponse2002
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-18T20:07:26.808629200Z[GMT]")


public class InlineResponse2002 {
    @JsonProperty("status")
    private String status = null;

    public InlineResponse2002 status(String status) {
        this.status = status;
        return this;
    }

    public InlineResponse2002(String status) {
        this.status = status;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @Schema(example = "Pizza id 1 was added to favorites user id 1", description = "")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InlineResponse2002 inlineResponse2002 = (InlineResponse2002) o;
        return Objects.equals(this.status, inlineResponse2002.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InlineResponse2002 {\n");

        sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
