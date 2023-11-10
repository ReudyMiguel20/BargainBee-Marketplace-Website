package com.bargainbee.itemlistingservice.service;

import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
import com.bargainbee.itemlistingservice.model.dto.ItemStatus;
import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemDto;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Item;

import java.util.List;

public interface ItemListingService {
    ItemInfo createItem(NewItemDto item);

    List<Item> getItemsByCategory(Category category);

    void generateAndSetUUIDCode(Item itemToBeListed);

    void setAvailability(Item itemToBeListed);

    void setListingDate(Item itemToBeListed);

    ItemInfo updateItem(String itemId, ItemUpdatedDto itemUpdatedDto);

    void updateItemValues(Item item, ItemUpdatedDto itemUpdatedDto);

    ItemStatus deleteItem(String itemId);

    List<Item> getFeaturedItems();

    List<Item> getRelatedItems(String itemId);
}
