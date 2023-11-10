package com.reudymiguel.itemlistingservice.service.impl;

import com.reudymiguel.itemlistingservice.model.dto.ItemInfo;
import com.reudymiguel.itemlistingservice.model.dto.ItemUpdatedDto;
import com.reudymiguel.itemlistingservice.model.dto.NewItemDto;
import com.reudymiguel.itemlistingservice.model.entity.Category;
import com.reudymiguel.itemlistingservice.model.entity.Item;
import com.reudymiguel.itemlistingservice.repository.ItemListingRepository;
import com.reudymiguel.itemlistingservice.service.ItemListingService;
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
     *
     * @param newItemDto
     * @return
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

    @Override
    public ItemInfo updateItem(String itemId, ItemUpdatedDto itemUpdatedDto) {
        Item itemToUpdate = itemListingRepository.findItemByItemId(itemId).orElseThrow();

        // Set respective values to the item
        updateItemValues(itemToUpdate, itemUpdatedDto);

        itemListingRepository.save(itemToUpdate);

        return modelMapper.map(itemToUpdate, ItemInfo.class);
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
