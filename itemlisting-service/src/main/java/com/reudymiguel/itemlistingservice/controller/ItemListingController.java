package com.reudymiguel.itemlistingservice.controller;

import com.reudymiguel.itemlistingservice.model.dto.ItemStatus;
import com.reudymiguel.itemlistingservice.model.dto.ItemUpdatedDto;
import com.reudymiguel.itemlistingservice.model.dto.NewItemDto;
import com.reudymiguel.itemlistingservice.model.dto.ItemInfo;
import com.reudymiguel.itemlistingservice.model.entity.Category;
import com.reudymiguel.itemlistingservice.model.entity.Item;
import com.reudymiguel.itemlistingservice.service.ItemListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemInfo updateItem(@PathVariable String itemId, @RequestBody ItemUpdatedDto itemUpdatedDto) {
        return itemListingService.updateItem(itemId, itemUpdatedDto);
    }

    @DeleteMapping("/delete/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemStatus deleteItem(@PathVariable String itemId) {
        return itemListingService.deleteItem(itemId);
    }

    @GetMapping("/category/{categoryName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getAllItemsByCategory(@PathVariable String categoryName) {
        Category category = Category.valueOf(categoryName.toUpperCase());
        return itemListingService.getItemsByCategory(category);
    }

    @GetMapping("/featured")
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getAllFeaturedItems() {
        return itemListingService.getFeaturedItems();
    }





}
