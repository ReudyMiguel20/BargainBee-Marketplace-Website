package com.bargainbee.itemlistingservice.service;

import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemRequest;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Item;

import java.util.List;

public interface ItemListingService {
    ItemInfo createItem(NewItemRequest item);

    List<Item> getItemsByCategory(Category category);

    Item generateAndSetUUIDCode(Item itemToBeListed);

    void setAvailability(Item itemToBeListed);

    void setListingDate(Item itemToBeListed);

    ItemInfo updateItem(String itemId, ItemUpdatedDto itemUpdatedDto);

    void updateItemValues(Item item, ItemUpdatedDto itemUpdatedDto);

    void deleteItem(String itemId);

    List<Item> getFeaturedItems();

    List<Item> getRelatedItems(String itemId);

    List<Item> searchItemsByKeyword(String keyword);

    List<Item> getAllItems();

    ItemInfo getItemByItemId(String itemId);
}
