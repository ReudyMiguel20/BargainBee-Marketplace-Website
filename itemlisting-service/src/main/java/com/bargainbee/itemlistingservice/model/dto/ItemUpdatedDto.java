package com.bargainbee.itemlistingservice.model.dto;

import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdatedDto {

    @JsonProperty(value = "item_name")
    @NotNull(message = "Item name cannot be null")
    private String itemName;

    @NotEmpty(message = "Description cannot be empty")
    @NotNull(message = "Description cannot be null")
    private String description;

    @NotEmpty(message = "Condition cannot be empty")
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.50", inclusive = false, message = "Price should be greater than 0.50")
    private double price;

    @NotEmpty(message = "Condition cannot be empty")
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity should be greater than 0")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Condition cannot be empty")
    @NotNull(message = "Category cannot be null")
    private Category category;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Condition cannot be empty")
    @NotNull(message = "Condition cannot be null")
    private Condition condition;

    @NotNull(message = "Image cannot be null")
    @NotEmpty(message = "Image cannot be empty")
    private String image;

    @NotEmpty(message = "Tags cannot be empty")
    @NotNull(message = "Tags cannot be null")
    private String tags;

}
