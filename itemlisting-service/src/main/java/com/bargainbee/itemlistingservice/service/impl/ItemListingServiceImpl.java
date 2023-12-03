package com.bargainbee.itemlistingservice.service.impl;

import com.bargainbee.itemlistingservice.common.misc.JwtDecoder;
import com.bargainbee.itemlistingservice.exception.ItemNotFoundException;
import com.bargainbee.itemlistingservice.exception.UnauthorizedItemModificationException;
import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemRequest;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.bargainbee.itemlistingservice.model.entity.Item;
import com.bargainbee.itemlistingservice.repository.ItemListingRepository;
import com.bargainbee.itemlistingservice.service.ItemListingService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final JwtDecoder jwtDecoder;

    /**
     * Creates a new item and saves it to the database
     *
     * @param newItemRequest - DTO containing the new item information
     * @return - ItemInfo containing the new item information
     */
    @Override
    public ItemInfo createItem(NewItemRequest newItemRequest) {
        Item newItem = modelMapper.map(newItemRequest, Item.class);

        // Set respective values to the item
        generateAndSetUUIDCode(newItem);
        setAvailability(newItem);
        setListingDate(newItem);

        itemListingRepository.save(newItem);

        return modelMapper.map(newItem, ItemInfo.class);
    }

    @Override
    public ItemInfo createItem(NewItemRequest newItemRequest, String token) throws JsonProcessingException {
        Item newItem = modelMapper.map(newItemRequest, Item.class);

        // Set respective values to the item
        setSeller(newItem, jwtDecoder.decodeJwtAndGetUsername(token));
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
        Item itemToUpdate = itemListingRepository.findItemByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        updateItemValues(itemToUpdate, itemUpdatedDto);
        itemListingRepository.save(itemToUpdate);

        return modelMapper.map(itemToUpdate, ItemInfo.class);
    }

    @Override
    public ItemInfo updateItem(String itemId, ItemUpdatedDto itemUpdatedDto, String token) throws JsonProcessingException {
        Item itemToUpdate = itemListingRepository.findItemByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        // Verify that the user is the seller of the item
        verifySeller(itemToUpdate, jwtDecoder.decodeJwtAndGetUsername(token));

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
    public void deleteItem(String itemId) {
        Item itemToDelete = itemListingRepository.findItemByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        itemListingRepository.delete(itemToDelete);
    }

    @Override
    public void deleteItem(String itemId, String token) throws JsonProcessingException {
        Item itemToDelete = itemListingRepository.findItemByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        // Verify that the user is the seller of the item
        verifySeller(itemToDelete, jwtDecoder.decodeJwtAndGetUsername(token));

        itemListingRepository.delete(itemToDelete);
    }

    @Override
    public List<ItemInfo> getFeaturedItems() {
        return itemListingRepository.findFeaturedItems()
                .stream()
                .map(item -> modelMapper.map(item, ItemInfo.class))
                .toList();
    }

    // Handle not returning the item that is being viewed
    @Override
    public List<ItemInfo> getRelatedItems(String itemId) {
        Item item = itemListingRepository.findItemByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        Category category = item.getCategory();

        return itemListingRepository.findRelatedItems(itemId, category)
                .stream()
                .map(item1 -> modelMapper.map(item1, ItemInfo.class))
                .toList();
    }

    @Override
    public List<ItemInfo> getItemsByCategory(Category category) {
        return itemListingRepository.findItemsByCategory(category).stream()
                .map(item -> modelMapper.map(item, ItemInfo.class))
                .toList();
    }

    @Override
    public Item generateAndSetUUIDCode(Item item) {
        String code = UUID.randomUUID().toString();
        item.setItemId(code);
        return item;
    }

    @Override
    public void setAvailability(Item item) {
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
        item.setDescription(itemUpdatedDto.getDescription());
        item.setPrice(itemUpdatedDto.getPrice());
        item.setQuantity(itemUpdatedDto.getQuantity());
        item.setCategory(itemUpdatedDto.getCategory());
        item.setCondition(itemUpdatedDto.getCondition());
        item.setImage(itemUpdatedDto.getImage());
        item.setTags(itemUpdatedDto.getTags());
    }

    @Override
    public List<ItemInfo> searchItemsByKeyword(String keyword) {
        return itemListingRepository.findItemsByItemNameContainingIgnoreCase(keyword)
                .stream()
                .map(item -> modelMapper.map(item, ItemInfo.class))
                .toList();
    }

    @Override
    public List<ItemInfo> getAllItems() {
        return itemListingRepository.findAll()
                .stream()
                .map(item -> modelMapper.map(item, ItemInfo.class))
                .toList();
    }

    @Override
    public ItemInfo getItemByItemId(String itemId) {
        Item item = itemListingRepository.findItemByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        return modelMapper.map(item, ItemInfo.class);
    }

    @Override
    public List<ItemInfo> getItemsByPriceBetween(double minPrice, double maxPrice) {
        return itemListingRepository.findItemsByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(item -> modelMapper.map(item, ItemInfo.class))
                .toList();
    }

    @Override
    public List<ItemInfo> getFilteredItems(String itemName, String categoryString, String conditionString, int minQuantity, int maxQuantity, double minPrice, double maxPrice, boolean featured) {
        itemName = assignItemNameOrNull(itemName);
        Category category = convertStringToCategory(categoryString);
        Condition condition = convertStringToCondition(conditionString);

        return itemListingRepository.findFilteredItems(itemName, category, condition, minQuantity, maxQuantity, minPrice, maxPrice, featured)
                .stream()
                .map(item -> modelMapper.map(item, ItemInfo.class))
                .toList();
    }

    @Override
    public List<ItemInfo> getItemsBySeller(String seller) {
        return itemListingRepository.findItemsBySeller(seller)
                .stream()
                .map(item -> modelMapper.map(item, ItemInfo.class))
                .toList();
    }

    public Category convertStringToCategory(String categoryString) {
        if (categoryString.isEmpty()) return null;

        categoryString = categoryString.toUpperCase();
        return Category.valueOf(categoryString);
    }

    public Condition convertStringToCondition(String conditionString) {
        if (conditionString.isEmpty()) return null;

        conditionString = conditionString.toUpperCase();
        return Condition.valueOf(conditionString);
    }

    public String assignItemNameOrNull(String itemName) {
        if (itemName.isEmpty()) return null;
        return itemName;
    }

    public void setSeller(Item item, String username) {
        item.setSeller(username);
    }

    public void verifySeller(Item item, String username) {
        if (!item.getSeller().equals(username)) {
            throw new UnauthorizedItemModificationException();
        }
    }

    @Override
    public void updateItemQuantity(String itemId, int quantity) {
        Item itemToUpdate = itemListingRepository.findItemByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        itemToUpdate.setQuantity(quantity);

        itemListingRepository.save(itemToUpdate);
    }


}
