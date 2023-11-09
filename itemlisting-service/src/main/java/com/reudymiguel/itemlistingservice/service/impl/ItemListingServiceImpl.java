package com.reudymiguel.itemlistingservice.service.impl;

import com.reudymiguel.itemlistingservice.model.dto.ItemInfo;
import com.reudymiguel.itemlistingservice.model.dto.NewItemDto;
import com.reudymiguel.itemlistingservice.model.entity.Item;
import com.reudymiguel.itemlistingservice.repository.ItemListingRepository;
import com.reudymiguel.itemlistingservice.service.ItemListingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
}
