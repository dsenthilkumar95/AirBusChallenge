package com.airbus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "category",
        "name",
        "description",
        "units"
})
public class ProductDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("category")
    @NotNull(message = "Category cannot be null")
    @NotEmpty(message = "Category cannot be empty")
    private String category;
    @JsonProperty("name")
    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("units")
    @Min(value = 0, message = "Units cannot be less than 0")
    @Max(value = 100, message = "Units cannot be more than 100")
    @NotNull(message = "Units cannot be null")
    private Integer units;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", units=" + units +
                '}';
    }
}
