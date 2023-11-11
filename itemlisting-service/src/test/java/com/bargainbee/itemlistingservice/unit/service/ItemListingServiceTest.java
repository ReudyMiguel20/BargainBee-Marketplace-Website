package com.bargainbee.itemlistingservice.unit.service;

import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private NewItemRequest createNewItemRequest(Category category, Condition condition) {
        return NewItemRequest.builder()
                .itemName("testItem")
                .description("testDescription")
                .price(100.0)
                .quantity(10)
                .category(category)
                .condition(condition)
                .image("testImage")
                .tags("testTags")
                .build();
    }

    private Item createNewItem(NewItemRequest newItemRequest) {
        return Item.builder()
                .itemId(UUID.randomUUID().toString())
                .itemName(newItemRequest.getItemName())
                .description(newItemRequest.getDescription())
                .price(newItemRequest.getPrice())
                .quantity(newItemRequest.getQuantity())
                .category(newItemRequest.getCategory())
                .condition(newItemRequest.getCondition())
                .image(newItemRequest.getImage())
                .tags(newItemRequest.getTags())
                .build();
    }

    private ItemInfo createExpectedItemInfo(Item item) {
        return ItemInfo.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .category(item.getCategory())
                .condition(item.getCondition())
                .image(item.getImage())
                .tags(item.getTags())
                .build();
    }

    @Test
    void shouldCreateItemSuccessfully() {
        // Arrange
        NewItemRequest newItemRequest = createNewItemRequest(Category.ELECTRONICS, Condition.NEW);
        Item newItem = createNewItem(newItemRequest);
        ItemInfo expectedItemInfo = createExpectedItemInfo(newItem);

        when(modelMapper.map(newItemRequest, Item.class)).thenReturn(newItem);
        when(itemListingRepository.save(newItem)).thenReturn(newItem);
        when(modelMapper.map(newItem, ItemInfo.class)).thenReturn(expectedItemInfo);

        // Act
        ItemInfo createdItemInfo = itemListingService.createItem(newItemRequest);

        // Assert
        Assertions.assertThat(createdItemInfo.getItemId()).isEqualTo(newItem.getItemId());
        Assertions.assertThat(createdItemInfo).isNotNull();

        // Verify
        verify(itemListingRepository).save(newItem);
        verify(modelMapper).map(newItemRequest, Item.class);
        verify(modelMapper).map(newItem, ItemInfo.class);
    }

}