package com.bargainbee.itemlistingservice.model.dto;

import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String itemId;

    @JsonProperty(value = "item_name")
    private String itemName;

    private String description;

    private double price;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    private String image;

    private boolean available;

    private boolean featured;

    @JsonProperty(value = "date_listed")
    private LocalDate dateListed;

    private String tags;
}
