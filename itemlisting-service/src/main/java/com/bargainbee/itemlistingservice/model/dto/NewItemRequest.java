package com.bargainbee.itemlistingservice.model.dto;

import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewItemRequest {

    @JsonProperty(value = "item_name")
    @NotEmpty(message = "Item name cannot be empty")
    @NotNull(message = "Item name cannot be null")
    @Size(min = 5, message = "Item name must be at least 3 characters long")
    private String itemName;

    @NotEmpty(message = "Description cannot be empty")
    @NotNull(message = "Description cannot be null")
    @Size(min = 5, message = "Description must be at least 3 characters long")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.50", inclusive = false, message = "Price should be greater than 0.50")
    private Double price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity should be greater than 0")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Category cannot be null")
    private Category category;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Condition cannot be null")
    private Condition condition;

//    private User seller;

    @NotEmpty(message = "Image cannot be empty")
    @NotNull(message = "Image cannot be null")
    private String image;

    @NotEmpty(message = "Tags cannot be empty")
    @NotNull(message = "Tags cannot be null")
    @Size(min = 3, message = "The entire tag must be at least 3 characters long")
    private String tags;

}
