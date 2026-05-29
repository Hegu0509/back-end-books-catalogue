package com.unir.books.catalogue.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isbn",
        "title",
        "description",
        "shortDescription",
        "authorId",
        "publisherId",
        "categoryId",
        "publicationDate",
        "edition",
        "language",
        "format",
        "pages",
        "price",
        "stock",
        "images"
})
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WriteBookRequestDto implements Serializable {

    private final static long serialVersionUID = 7686450847709803303L;

    @JsonProperty("isbn")
    public String isbn;

    @JsonProperty("title")
    public String title;

    @JsonProperty("description")
    public String description;

    @JsonProperty("shortDescription")
    public String shortDescription;

    @JsonProperty("authorId")
    public Long authorId;

    @JsonProperty("publisherId")
    public Long publisherId;

    @JsonProperty("categoryId")
    public Long categoryId;

    @JsonProperty("publicationDate")
    public LocalDate publicationDate;

    @JsonProperty("edition")
    public String edition;

    @JsonProperty("language")
    public String language;

    @JsonProperty("format")
    public String format;

    @JsonProperty("pages")
    public Integer pages;

    @JsonProperty("price")
    public BigDecimal price;

    @JsonProperty("stock")
    public Integer stock;

    @JsonProperty("images")
    public List<ImageDto> images;

}
