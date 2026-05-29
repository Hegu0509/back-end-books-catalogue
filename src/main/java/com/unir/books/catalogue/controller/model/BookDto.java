package com.unir.books.catalogue.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "isbn",
        "valoracion",
        "price",
        "stock"
})
@Getter
@Setter
@Builder
public class BookDto implements Serializable {

    private final static long serialVersionUID = 1901178943784643027L;

    @JsonProperty("id")
    public Long id;
    @JsonProperty("title")
    public String title;
    @JsonProperty("description")
    public String description;
    @JsonProperty("isbn")
    public String isbn;
    @JsonProperty("valoracion")
    public BigDecimal valoracion;
    @JsonProperty("price")
    public BigDecimal price;
    @JsonProperty("stock")
    public Integer stock;
}
