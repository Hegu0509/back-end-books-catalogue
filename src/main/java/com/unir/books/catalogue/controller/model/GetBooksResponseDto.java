package com.unir.books.catalogue.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "books"
})
@Getter
@Setter
@Builder
public class GetBooksResponseDto implements Serializable {

    private final static long serialVersionUID = 8761235707215843524L;
    @JsonProperty("books")
    public List<BookDto> books;
}
