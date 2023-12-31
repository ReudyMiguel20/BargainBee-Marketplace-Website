package com.bargainbee.itemlistingservice.controller;

import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemRequest;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.service.ItemListingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemListingController {

    private final ItemListingService itemListingService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemInfo createItemPost(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @Valid @RequestBody NewItemRequest item
    ) throws JsonProcessingException {
        return itemListingService.createItem(item, authorizationHeader);
    }

    @PutMapping("/update/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemInfo updateItem(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable String itemId,
            @RequestBody ItemUpdatedDto itemUpdatedDto
    ) throws JsonProcessingException {
        return itemListingService.updateItem(itemId, itemUpdatedDto, authorizationHeader);
    }

    @DeleteMapping("/delete/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable String itemId
    ) throws JsonProcessingException {
        itemListingService.deleteItem(itemId, authorizationHeader);
    }

    @GetMapping("/category/{categoryName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemInfo> getAllItemsByCategory(@PathVariable String categoryName) {
        Category category = Category.valueOf(categoryName.toUpperCase());
        return itemListingService.getItemsByCategory(category);
    }

    @GetMapping("/featured")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemInfo> getAllFeaturedItems() {
        return itemListingService.getFeaturedItems();
    }

    @GetMapping("/related/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemInfo> getAllRelatedItems(@PathVariable String itemId) {
        return itemListingService.getRelatedItems(itemId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemInfo> getSearchItemsByKeyword(@RequestParam("item-name") String itemName) {
        return itemListingService.searchItemsByKeyword(itemName);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemInfo> getAllItems() {
        return itemListingService.getAllItems();
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemInfo getItemDetails(@PathVariable String itemId) {
        return itemListingService.getItemByItemId(itemId);
    }

    @GetMapping("/price")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemInfo> getItemsByPriceBetween(
            @RequestParam("min-price") double minPrice,
            @RequestParam("max-price") double maxPrice
    ) {
        return itemListingService.getItemsByPriceBetween(minPrice, maxPrice);
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemInfo> getFilteredItems(
            @RequestParam(value = "item-name", required = false, defaultValue = "") String itemName,
            @RequestParam(value = "category", required = false, defaultValue = "") String categoryString,
            @RequestParam(value = "condition", required = false, defaultValue = "") String conditionString,
            @RequestParam(value = "min-quantity", required = false, defaultValue = "1") int minQuantity,
            @RequestParam(value = "max-quantity", required = false, defaultValue = "9999999") int maxQuantity,
            @RequestParam(value = "min-price", required = false, defaultValue = "0.00") double minPrice,
            @RequestParam(value = "max-price", required = false, defaultValue = "99999.99") double maxPrice,
            @RequestParam(value = "featured", required = false, defaultValue = "false") boolean featured
    ) {
        return itemListingService.getFilteredItems(
                itemName,
                categoryString,
                conditionString,
                minQuantity,
                maxQuantity,
                minPrice,
                maxPrice,
                featured
        );
    }

    @GetMapping("/seller/{seller}")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemInfo> getItemsBySeller(@PathVariable String seller) {
        return itemListingService.getItemsBySeller(seller);
    }

}
