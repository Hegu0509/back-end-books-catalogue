package com.unir.books.catalogue.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "isbn",
        "valoracion",
        "stock",
        "author",
        "publisher",
        "category",
        "images"
})
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBookResponseDto implements Serializable {

    private final static long serialVersionUID = 7686450847709803303L;

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
    @JsonProperty("stock")
    public Integer stock;
    @JsonProperty("author")
    public String author;
    @JsonProperty("publisher")
    public String publisher;
    @JsonProperty("category")
    public String category;
    @JsonProperty("images")
    public List<String> images;
}
