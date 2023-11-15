package com.bargainbee.itemlistingservice.service;

import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemRequest;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Item;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ItemListingService {
    ItemInfo createItem(NewItemRequest item);

    ItemInfo createItem(NewItemRequest item, String token) throws JsonProcessingException;

    List<ItemInfo> getItemsByCategory(Category category);

    Item generateAndSetUUIDCode(Item itemToBeListed);

    void setAvailability(Item itemToBeListed);

    void setListingDate(Item itemToBeListed);

    ItemInfo updateItem(String itemId, ItemUpdatedDto itemUpdatedDto);

    ItemInfo updateItem(String itemId, ItemUpdatedDto itemUpdatedDto, String token) throws JsonProcessingException;

    void updateItemValues(Item item, ItemUpdatedDto itemUpdatedDto);

    void deleteItem(String itemId);

    void deleteItem(String itemId, String token) throws JsonProcessingException;

    List<ItemInfo> getFeaturedItems();

    List<ItemInfo> getRelatedItems(String itemId);

    List<ItemInfo> searchItemsByKeyword(String keyword);

    List<ItemInfo> getAllItems();

    ItemInfo getItemByItemId(String itemId);

    List<ItemInfo> getItemsByPriceBetween(double minPrice, double maxPrice);

    List<ItemInfo> getFilteredItems(String itemName, String categoryString, String conditionString, int minQuantity, int maxQuantity, double minPrice, double maxPrice, boolean featured);

    List<ItemInfo> getItemsBySeller(String seller);

    void updateItemQuantity(String itemId, int quantity);
}
