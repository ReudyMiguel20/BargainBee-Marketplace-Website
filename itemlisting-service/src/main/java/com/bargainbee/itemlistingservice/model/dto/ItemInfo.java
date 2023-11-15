package com.bargainbee.itemlistingservice.model.dto;

import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfo {

    @JsonProperty(value = "item_id")
    @NotEmpty(message = "Condition cannot be empty")
    @NotNull(message = "Condition cannot be null")
    private String itemId;

    @JsonProperty(value = "item_name")
    @NotEmpty(message = "Item name cannot be empty")
    @NotNull(message = "Item name cannot be null")
    private String itemName;

    @NotEmpty(message = "Description cannot be empty")
    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Price cannot be null")
    @NotEmpty(message = "Price cannot be empty")
    private double price;

    @NotNull(message = "Quantity cannot be null")
    @NotEmpty(message = "Quantity cannot be empty")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Category cannot be empty")
    @NotNull(message = "Category cannot be null")
    private Category category;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Condition cannot be empty")
    @NotNull(message = "Condition cannot be null")
    private Condition condition;

    @NotEmpty(message = "Seller cannot be empty")
    @NotNull(message = "Seller cannot be null")
    private String seller;

    @NotEmpty(message = "Image cannot be empty")
    @NotNull(message = "Image cannot be null")
    private String image;

    @NotNull(message = "Available cannot be null")
    @NotEmpty(message = "Available cannot be empty")
    private boolean available;

    @NotNull(message = "Featured cannot be null")
    @NotEmpty(message = "Featured cannot be empty")
    private boolean featured;

    @JsonProperty(value = "date_listed")
    @NotNull(message = "Date listed cannot be null")
    @NotEmpty(message = "Date listed cannot be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateListed;

    @NotNull(message = "Tags cannot be null")
    @NotEmpty(message = "Tags cannot be empty")
    private String tags;
}
