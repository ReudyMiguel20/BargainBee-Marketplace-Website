package com.bargainbee.itemlistingservice.unit.service;

import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemRequest;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.bargainbee.itemlistingservice.model.entity.Item;
import com.bargainbee.itemlistingservice.repository.ItemListingRepository;
import com.bargainbee.itemlistingservice.service.impl.ItemListingServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
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


    // Tests


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
    void updateItemValues() {
        // Arrange
        Item itemToUpdate = new Item();

        ItemUpdatedDto itemUpdatedDto = ItemUpdatedDto.builder()
                .itemName("New Item Name")
                .category(Category.ELECTRONICS)
                .condition(Condition.NEW)
                .description("New Description")
                .price(100.00)
                .quantity(10)
                .image("test.jpg")
                .tags("New Tag").build();

        // Act
        itemListingService.updateItemValues(itemToUpdate, itemUpdatedDto);

        // Assert
        Assertions.assertThat(itemToUpdate.getItemName()).isEqualTo(itemUpdatedDto.getItemName());
        Assertions.assertThat(itemToUpdate.getCategory()).isEqualTo(itemUpdatedDto.getCategory());
        Assertions.assertThat(itemToUpdate.getCondition()).isEqualTo(itemUpdatedDto.getCondition());
        Assertions.assertThat(itemToUpdate.getDescription()).isEqualTo(itemUpdatedDto.getDescription());
        Assertions.assertThat(itemToUpdate.getPrice()).isEqualTo(itemUpdatedDto.getPrice());
        Assertions.assertThat(itemToUpdate.getQuantity()).isEqualTo(itemUpdatedDto.getQuantity());
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
    void deleteItem() {
        // Arrange
        String itemId = UUID.randomUUID().toString();
        Item itemToDelete = new Item();

        when(itemListingRepository.findItemByItemId(itemId)).thenReturn(Optional.of(itemToDelete));

        // Act
        itemListingService.deleteItem(itemId);

        // Verify
        verify(itemListingRepository, times(1)).findItemByItemId(itemId);
        verify(itemListingRepository, times(1)).delete(itemToDelete);
    }

    @Test
    void getFeaturedItems() {
        // Arrange
        Item item = new Item();
        item.setFeatured(true);
        when(itemListingRepository.findFeaturedItems()).thenReturn(java.util.List.of(item));

        // Act
        List<Item> result = itemListingService.getFeaturedItems();

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).isFeatured()).isTrue();

        // Verify
        verify(itemListingRepository, times(1)).findFeaturedItems();
    }

    @Test
    void getRelatedItems() {
        // Arrange
        String itemId = UUID.randomUUID().toString();
        Item item = new Item();
        item.setCategory(Category.ELECTRONICS);
        when(itemListingRepository.findItemByItemId(itemId)).thenReturn(Optional.of(item));
        when(itemListingRepository.findRelatedItems(itemId, Category.ELECTRONICS)).thenReturn(java.util.List.of(item));

        // Act
        List<Item> result = itemListingService.getRelatedItems(itemId);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getCategory()).isEqualTo(Category.ELECTRONICS);

        // Verify
        verify(itemListingRepository, times(1)).findItemByItemId(itemId);
        verify(itemListingRepository, times(1)).findRelatedItems(itemId, Category.ELECTRONICS);
    }

    @Test
    void getItemsByCategory() {
        // Arrange
        Category category = Category.ELECTRONICS;
        Item item = new Item();
        item.setCategory(Category.ELECTRONICS);
        when(itemListingRepository.findItemsByCategory(category)).thenReturn(java.util.List.of(item));

        // Act
        List<Item> result = itemListingService.getItemsByCategory(category);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getCategory()).isEqualTo(Category.ELECTRONICS);

        // Verify
        verify(itemListingRepository, times(1)).findItemsByCategory(category);
    }

    @Test
    void generateAndSetUUIDCode() {
        // Arrange
        Item item = new Item();

        // Act
        Item result = itemListingService.generateAndSetUUIDCode(item);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getItemId()).isNotNull();
    }

    @Test
    void setAvailability() {
        // Arrange
        Item item = new Item();
        item.setQuantity(10);

        // Act
        itemListingService.setAvailability(item);

        // Assert
        Assertions.assertThat(item.isAvailable()).isTrue();
    }

    @Test
    void setListingDate() {
        // Arrange
        Item item = new Item();

        // Act
        itemListingService.setListingDate(item);

        // Assert
        Assertions.assertThat(item.getDateListed()).isNotNull();
        Assertions.assertThat(item.getDateListed()).isInstanceOf(LocalDate.class);
    }

}