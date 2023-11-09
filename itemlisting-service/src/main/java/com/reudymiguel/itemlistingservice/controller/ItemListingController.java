package com.reudymiguel.itemlistingservice.controller;

import com.reudymiguel.itemlistingservice.model.dto.NewItemDto;
import com.reudymiguel.itemlistingservice.model.dto.ItemInfo;
import com.reudymiguel.itemlistingservice.service.ItemListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemListingController {

    private final ItemListingService itemListingService;

    // Remember to get the user email/name from the request header
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemInfo createItemPost(@RequestBody NewItemDto item) {
        return itemListingService.createItem(item);
    }
}
