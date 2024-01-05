package com.bargainbee.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderDto {

    @JsonProperty(value = "item_id")
    private int itemId;

    @JsonProperty(value = "item_quantity")
    private int itemQuantity;

    private String buyer;

    private String seller;

}
