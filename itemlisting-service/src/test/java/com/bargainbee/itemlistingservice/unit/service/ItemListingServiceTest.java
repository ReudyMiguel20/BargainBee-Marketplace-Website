package com.bargainbee.itemlistingservice.unit.service;

import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemRequest;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.bargainbee.itemlistingservice.model.entity.Item;
import com.bargainbee.itemlistingservice.repository.ItemListingRepository;
import com.bargainbee.itemlistingservice.service.ItemListingService;
import com.bargainbee.itemlistingservice.service.impl.ItemListingServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Testcontainers
@SpringBootTest
class ItemListingServiceTest {

    @InjectMocks
    private ItemListingServiceImpl itemListingService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ItemListingRepository itemListingRepository;




    @Test
    void createItem() {
        // Arrange
        NewItemRequest newItemRequest = new NewItemRequest();
        Item mockedItem = new Item();
        ItemInfo mockedItemInfo = new ItemInfo();

        when(modelMapper.map(newItemRequest, Item.class)).thenReturn(mockedItem);
        when(itemListingRepository.save(mockedItem)).thenReturn(mockedItem);
        when(modelMapper.map(mockedItem, ItemInfo.class)).thenReturn(mockedItemInfo);

        // Act
        ItemInfo result = itemListingService.createItem(newItemRequest);

        // Assert
        Assertions.assertThat(result).isNotNull();
        verify(itemListingRepository, times(1)).save(mockedItem);
        verify(modelMapper, times(1)).map(newItemRequest, Item.class);
        verify(modelMapper, times(1)).map(mockedItem, ItemInfo.class);
    }

    @Test
    void updateItem() {
        // Arrange
        String itemId = UUID.randomUUID().toString();
        Item itemToUpdate = new Item();
        ItemUpdatedDto itemUpdatedDto = new ItemUpdatedDto();
        ItemInfo mockedUpdatedItemInfo = new ItemInfo();

        when(itemListingRepository.findItemByItemId(itemId)).thenReturn(Optional.of(itemToUpdate));
        when(itemListingRepository.save(any())).thenReturn(itemToUpdate);
        when(modelMapper.map(itemToUpdate, ItemInfo.class)).thenReturn(mockedUpdatedItemInfo);

        // Act
        ItemInfo result = itemListingService.updateItem(itemId, itemUpdatedDto);

        // Assert
        Assertions.assertThat(result).isNotNull();

        // Verify
        verify(itemListingRepository, times(1)).findItemByItemId(itemId);
        verify(itemListingRepository, times(1)).save(itemToUpdate);
        verify(modelMapper, times(1)).map(itemToUpdate, ItemInfo.class);
    }

    @Test
    void updateItemValues() {
        // Arrange
        Item itemToUpdate = new Item();
        ItemUpdatedDto itemUpdatedDto = new ItemUpdatedDto();
        itemUpdatedDto.setItemName("New Item Name");
        itemUpdatedDto.setCategory(Category.ELECTRONICS);
        itemUpdatedDto.setCondition(Condition.NEW);
        itemUpdatedDto.setDescription("New Description");
        itemUpdatedDto.setPrice(100.00);
        itemUpdatedDto.setQuantity(10);

        // Act
        itemListingService.updateItemValues(itemToUpdate, itemUpdatedDto);

        // Assert
        Assert.assertEquals(itemToUpdate.getItemName(), itemUpdatedDto.getItemName());
        Assert.assertEquals(itemToUpdate.getCategory(), itemUpdatedDto.getCategory());
        Assert.assertEquals(itemToUpdate.getCondition(), itemUpdatedDto.getCondition());
        Assert.assertEquals(itemToUpdate.getDescription(), itemUpdatedDto.getDescription());
        Assert.assertEquals(itemToUpdate.getPrice(), itemUpdatedDto.getPrice());
        Assert.assertEquals(itemToUpdate.getQuantity(), itemUpdatedDto.getQuantity());
    }


}