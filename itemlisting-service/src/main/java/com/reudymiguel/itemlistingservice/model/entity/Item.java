package com.reudymiguel.itemlistingservice.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "t_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

//    private User seller;

    private String image;

    private boolean available;

    private boolean featured;

    @JsonProperty(value = "date_listed")
    private LocalDate dateListed;

    private String tags;
}
