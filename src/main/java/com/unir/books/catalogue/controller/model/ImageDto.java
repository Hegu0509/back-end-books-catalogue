package com.unir.books.catalogue.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "bookId",
        "imageUrl",
        "imageType",
        "imageOrder",
        "altText"

})
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageDto implements Serializable {

    private final static long serialVersionUID = 1901178943784643027L;

    @JsonProperty("bookId")
    private Long bookId;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("imageType")
    private String imageType;

    @JsonProperty("imageOrder")
    private Integer imageOrder;

    @JsonProperty("altText")
    private String altText;
}
