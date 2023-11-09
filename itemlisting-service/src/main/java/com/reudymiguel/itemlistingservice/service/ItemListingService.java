package com.reudymiguel.itemlistingservice.service;

import com.reudymiguel.itemlistingservice.model.dto.ItemInfo;
import com.reudymiguel.itemlistingservice.model.dto.NewItemDto;
import com.reudymiguel.itemlistingservice.model.entity.Item;

public interface ItemListingService {
    ItemInfo createItem(NewItemDto item);

    void generateAndSetUUIDCode(Item itemToBeListed);

    void setAvailability(Item itemToBeListed);

    void setListingDate(Item itemToBeListed);
}
