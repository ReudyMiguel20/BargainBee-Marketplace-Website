package com.bargainbee.itemlistingservice.service.impl;

import com.bargainbee.itemlistingservice.model.dto.ItemStatus;
import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemDto;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Item;
import com.bargainbee.itemlistingservice.repository.ItemListingRepository;
import com.bargainbee.itemlistingservice.service.ItemListingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemListingServiceImpl implements ItemListingService {

    private final ItemListingRepository itemListingRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new item and saves it to the database
     *
     * @param newItemDto - DTO containing the new item information
     * @return - ItemInfo containing the new item information
     */
    @Override
    public ItemInfo createItem(NewItemDto newItemDto) {
        Item newItem = modelMapper.map(newItemDto, Item.class);

        // Set respective values to the item
        generateAndSetUUIDCode(newItem);
        setAvailability(newItem);
        setListingDate(newItem);

        itemListingRepository.save(newItem);

        return modelMapper.map(newItem, ItemInfo.class);
    }

    /**
     * Updates an existing item and saves it to the database with the new values provided
     *
     * @param itemId         - Item ID of the item to be updated
     * @param itemUpdatedDto - DTO containing the new item information
     * @return - ItemInfo containing the updated item information
     */
    @Override
    public ItemInfo updateItem(String itemId, ItemUpdatedDto itemUpdatedDto) {
        Item itemToUpdate = itemListingRepository.findItemByItemId(itemId).orElseThrow();

        updateItemValues(itemToUpdate, itemUpdatedDto);
        itemListingRepository.save(itemToUpdate);

        return modelMapper.map(itemToUpdate, ItemInfo.class);
    }

    /**
     * Deletes an existing item from the database with the provided item ID and returns a status message
     *
     * @param itemId - Item ID of the item to be deleted
     * @return - ItemStatus containing the status message
     */
    @Override
    public ItemStatus deleteItem(String itemId) {
        Item itemToDelete = itemListingRepository.findItemByItemId(itemId).orElseThrow();

        itemListingRepository.delete(itemToDelete);

        return ItemStatus.builder()
                .itemId(itemId)
                .status("Item has been deleted successfully")
                .build();
    }

    @Override
    public List<Item> getFeaturedItems() {
        return itemListingRepository.findFeaturedItems();
    }

    @Override
    public List<Item> getRelatedItems(String itemId) {
        Item item = itemListingRepository.findItemByItemId(itemId).orElseThrow();
        Category category = item.getCategory();
        return itemListingRepository.findRelatedItems(itemId, category);
    }

    @Override
    public List<Item> getItemsByCategory(Category category) {
        return itemListingRepository.findItemsByCategory(category);
    }

    @Override
    public void generateAndSetUUIDCode(Item item) {
        String code = UUID.randomUUID().toString();
        item.setItemId(code);
    }

    @Override
    public void setAvailability(Item item) {
        // Maybe throw an exception here because in order to list
        // greater than 1 an item it must have a quantity
        item.setAvailable(item.getQuantity() >= 1);
    }

    @Override
    public void setListingDate(Item item) {
        item.setDateListed(LocalDate.now());
    }

    /**
     * Updates the values of an existing item with the new values provided in the DTO
     *
     * @param item           - Item to be updated
     * @param itemUpdatedDto - DTO containing the new item information
     */
    @Override
    public void updateItemValues(Item item, ItemUpdatedDto itemUpdatedDto) {
        item.setItemName(itemUpdatedDto.getItemName());
        item.setPrice(itemUpdatedDto.getPrice());
        item.setQuantity(itemUpdatedDto.getQuantity());
        item.setCategory(itemUpdatedDto.getCategory());
        item.setCondition(itemUpdatedDto.getCondition());
        item.setImage(itemUpdatedDto.getImage());
        item.setTags(itemUpdatedDto.getTags());
    }


}
