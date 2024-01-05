package com.bargainbee.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private int orderNumber;

    @JsonProperty(value = "item_id")
    private int itemId;

    @JsonProperty(value = "item_quantity")
    private int itemQuantity;

    private String seller;

    private String buyer;

}
