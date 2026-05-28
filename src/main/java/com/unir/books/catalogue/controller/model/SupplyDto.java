package com.unir.supplies.orders.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "description",
        "type",
        "price",
        "stock"
})
@Getter
@Setter
@Builder
public class SupplyDto implements Serializable {

    private final static long serialVersionUID = 1901178943784643027L;

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("type")
    public String type;
    @JsonProperty("price")
    public Double price;
    @JsonProperty("stock")
    public Integer stock;
}
