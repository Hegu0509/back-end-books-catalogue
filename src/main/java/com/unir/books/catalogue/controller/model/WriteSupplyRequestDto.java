package com.unir.supplies.orders.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "description",
        "fullDescription",
        "type",
        "price",
        "stock",
        "specifications",
        "images"
})
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WriteSupplyRequestDto implements Serializable {

    private final static long serialVersionUID = 7686450847709803303L;

    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("fullDescription")
    public String fullDescription;
    @JsonProperty("type")
    public String type;
    @JsonProperty("price")
    public Double price;
    @JsonProperty("stock")
    public Integer stock;
    @JsonProperty("specifications")
    public List<SpecificationDto> specificationDtos;
    @JsonProperty("images")
    public List<String> images;
}
