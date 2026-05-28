package com.unir.supplies.orders.controller.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "supplies"
})
@Getter
@Setter
@Builder
public class GetSuppliesResponseDto implements Serializable {

    private final static long serialVersionUID = 8761235707215843524L;
    @JsonProperty("supplies")
    public List<SupplyDto> supplies;
}
