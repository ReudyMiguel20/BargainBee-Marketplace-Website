package com.bargainbee.itemlistingservice.unit.service;

import com.bargainbee.itemlistingservice.exception.ItemNotFoundException;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
        Assertions.assertThat(result)
                .isNotNull();

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
    void shouldThrowExceptionOnMissingItemId() {
        // Arrange
        String itemId = UUID.randomUUID().toString();
        Item itemToUpdate = new Item();
        ItemUpdatedDto itemUpdatedDto = new ItemUpdatedDto();
        ItemInfo mockedUpdatedItemInfo = new ItemInfo();

        // Act
        when(itemListingRepository.findItemByItemId(itemId)).thenReturn(Optional.empty());

        // Assert
        Assertions.assertThatThrownBy(() -> itemListingService.updateItem(itemId, itemUpdatedDto))
                .isInstanceOf(ItemNotFoundException.class);

        // Verify
        verify(itemListingRepository, times(1)).findItemByItemId(itemId);
        verify(itemListingRepository, times(0)).save(itemToUpdate);
        verify(modelMapper, times(0)).map(itemToUpdate, ItemInfo.class);
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
    void shouldThrowExceptionWhenMissingItemIdDeleteItem() {
        // Arrange
        String itemId = UUID.randomUUID().toString();
        Item itemToDelete = new Item();

        when(itemListingRepository.findItemByItemId(itemId)).thenReturn(Optional.empty());

        // Act

        // Assert
        Assertions.assertThatThrownBy(() -> itemListingService.deleteItem(itemId))
                .isInstanceOf(ItemNotFoundException.class);

        // Verify
        verify(itemListingRepository, times(1)).findItemByItemId(itemId);
        verify(itemListingRepository, times(0)).delete(itemToDelete);
    }

    @Test
    void getFeaturedItems() {
        // Arrange
        Item item = new Item();
        item.setFeatured(true);

        when(modelMapper.map(item, ItemInfo.class)).thenReturn(ItemInfo.builder().featured(true).build());
        when(itemListingRepository.findFeaturedItems()).thenReturn(java.util.List.of(item));

        // Act
        List<ItemInfo> result = itemListingService.getFeaturedItems();

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).isFeatured()).isTrue();
        Assertions.assertThat(result.get(0)).isInstanceOf(ItemInfo.class);

        // Verify
        verify(itemListingRepository, times(1)).findFeaturedItems();
        verify(modelMapper, times(1)).map(item, ItemInfo.class);
    }


    @Test
    void getRelatedItems() {
        // Arrange
        Item firstItem = Item.builder()
                .itemId(UUID.randomUUID().toString())
                .category(Category.ELECTRONICS)
                .build();

        Item secondItem = Item.builder()
                .category(Category.ELECTRONICS)
                .build();


        when(itemListingRepository.findItemByItemId(firstItem.getItemId())).thenReturn(Optional.of(firstItem));
        when(itemListingRepository.findRelatedItems(firstItem.getItemId(), firstItem.getCategory())).thenReturn(List.of(firstItem, secondItem));
        when(modelMapper.map(firstItem, ItemInfo.class)).thenReturn(ItemInfo.builder().category(Category.ELECTRONICS).build());
        when(modelMapper.map(secondItem, ItemInfo.class)).thenReturn(ItemInfo.builder().category(Category.ELECTRONICS).build());

        // Act
        List<ItemInfo> result = itemListingService.getRelatedItems(firstItem.getItemId());

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getCategory()).isEqualTo(Category.ELECTRONICS);

        // Verify
        verify(itemListingRepository, times(1)).findItemByItemId(firstItem.getItemId());
        verify(itemListingRepository, times(1)).findRelatedItems(firstItem.getItemId(), firstItem.getCategory());
    }


    @Test
    void getItemsByCategory() {
        // Arrange
        Category category = Category.ELECTRONICS;
        Item item = new Item();
        item.setCategory(Category.ELECTRONICS);

        ItemInfo itemInfo = ItemInfo.builder()
                .category(Category.ELECTRONICS)
                .build();

        when(modelMapper.map(item, ItemInfo.class)).thenReturn(itemInfo);
        when(itemListingRepository.findItemsByCategory(category)).thenReturn(java.util.List.of(item));

        // Act
        List<ItemInfo> result = itemListingService.getItemsByCategory(category);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getCategory()).isEqualTo(Category.ELECTRONICS);
        Assertions.assertThat(result.get(0)).isInstanceOf(ItemInfo.class);

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


    @Test
    void shouldReturnItemsMatchingKeyword() {
        // Arrange
        String keyword = "laptop";

        Item firstItem = Item.builder()
                .itemName("Asus Laptop")
                .build();

        Item secondItem = Item.builder()
                .itemName("The best laptop")
                .build();

        when(itemListingRepository.findItemsByItemNameContainingIgnoreCase(keyword)).thenReturn(List.of(firstItem, secondItem));
        when(modelMapper.map(firstItem, ItemInfo.class)).thenReturn(ItemInfo.builder().itemName("Asus Laptop").build());
        when(modelMapper.map(secondItem, ItemInfo.class)).thenReturn(ItemInfo.builder().itemName("The best laptop").build());

        // Act
        List<ItemInfo> result = itemListingService.searchItemsByKeyword(keyword);

        // Assert
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isGreaterThan(0);

        Assertions.assertThat(result)
                .extracting(ItemInfo::getItemName)
                .contains("Asus Laptop", "The best laptop");

        Assertions.assertThat(result)
                .extracting(ItemInfo::getItemName)
                .allSatisfy(itemName -> assertThat(itemName.toLowerCase()).contains(keyword.toLowerCase()));

        Assertions.assertThat(result)
                .extracting(ItemInfo::getClass)
                .allMatch(itemClass -> itemClass.equals(ItemInfo.class));

        // Verify
        verify(itemListingRepository, times(1)).findItemsByItemNameContainingIgnoreCase(keyword);
        verify(modelMapper, times(1)).map(firstItem, ItemInfo.class);
        verify(modelMapper, times(1)).map(secondItem, ItemInfo.class);
    }


    @Test
    void shouldReturnAllItemsInAList() {
        // Arrange
        Item firstItem = Item.builder()
                .itemName("Asus Laptop")
                .build();

        Item secondItem = Item.builder()
                .itemName("The best laptop")
                .build();

        when(itemListingRepository.findAll()).thenReturn(List.of(firstItem, secondItem));
        when(modelMapper.map(firstItem, ItemInfo.class)).thenReturn(ItemInfo.builder().itemName("Asus Laptop").build());
        when(modelMapper.map(secondItem, ItemInfo.class)).thenReturn(ItemInfo.builder().itemName("The best laptop").build());

        // Act
        List<ItemInfo> result = itemListingService.getAllItems();

        // Assert
        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(result)
                .extracting(ItemInfo::getItemName)
                .contains("Asus Laptop", "The best laptop");

        Assertions.assertThat(result)
                .extracting(ItemInfo::getClass)
                .allMatch(itemClass -> itemClass.equals(ItemInfo.class));

        // Verify
        verify(itemListingRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(firstItem, ItemInfo.class);
        verify(modelMapper, times(1)).map(secondItem, ItemInfo.class);
    }

    @Test
    void shouldReturnItemDetails() {
        // Arrange
        Item firstItem = Item.builder()
                .itemId(UUID.randomUUID().toString())
                .build();

        Item secondItem = Item.builder()
                .itemId(UUID.randomUUID().toString())
                .build();

        ItemInfo firstItemInfo = ItemInfo.builder()
                .itemId(firstItem.getItemId())
                .build();

        ItemInfo secondItemInfo = ItemInfo.builder()
                .itemId(secondItem.getItemId())
                .build();

        when(itemListingRepository.findItemByItemId(firstItem.getItemId())).thenReturn(Optional.of(firstItem));
        when(itemListingRepository.findItemByItemId(secondItem.getItemId())).thenReturn(Optional.of(secondItem));
        when(modelMapper.map(firstItem, ItemInfo.class)).thenReturn(firstItemInfo);
        when(modelMapper.map(secondItem, ItemInfo.class)).thenReturn(secondItemInfo);

        // Act
        ItemInfo returnedItem = itemListingService.getItemByItemId(secondItem.getItemId());

        // Assert
        Assertions.assertThat(returnedItem).isNotNull();
        Assertions.assertThat(returnedItem.getItemId()).isEqualTo(secondItem.getItemId());

        // Verify
        verify(itemListingRepository, times(1)).findItemByItemId(secondItem.getItemId());
        verify(modelMapper, times(1)).map(secondItem, ItemInfo.class);
    }

    @Test
    void shouldReturnItemsWithinPriceRange() {
        // Arrange
        Item firstItem = Item.builder()
                .itemName("Asus Laptop")
                .price(1000.00)
                .build();

        Item secondItem = Item.builder()
                .itemName("Another laptop but cheaper")
                .price(900.00)
                .build();

        Item thirdItem = Item.builder()
                .itemName("The best laptop")
                .price(1600.00)
                .build();

        when(itemListingRepository.findItemsByPriceBetween(1000.00, 2000.00)).thenReturn(List.of(firstItem, thirdItem));

        when(modelMapper.map(firstItem, ItemInfo.class)).thenReturn(ItemInfo.builder()
                .itemName(firstItem.getItemName())
                .price(firstItem.getPrice())
                .build());

        when(modelMapper.map(secondItem, ItemInfo.class)).thenReturn(ItemInfo.builder()
                .itemName(secondItem.getItemName())
                .price(secondItem.getPrice())
                .build());

        when(modelMapper.map(thirdItem, ItemInfo.class)).thenReturn(ItemInfo.builder()
                .itemName("The best laptop")
                .price(thirdItem.getPrice())
                .build());

        // Act
        List<ItemInfo> result = itemListingService.getItemsByPriceBetween(1000.00, 2000.00);

        // Assert
        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(result)
                .extracting(ItemInfo::getItemName)
                .contains("Asus Laptop", "The best laptop");

        Assertions.assertThat(result)
                .extracting(ItemInfo::getPrice)
                .allMatch(price -> price >= 1000.00 && price <= 2000.00);

        // Verify
        verify(itemListingRepository, times(1)).findItemsByPriceBetween(1000.00, 2000.00);
        verify(modelMapper, times(1)).map(firstItem, ItemInfo.class);
        verify(modelMapper, times(1)).map(thirdItem, ItemInfo.class);
    }

    @Test
    void shouldReturnFilteredItems() {
        Item firstItem = Item.builder()
                .itemName("Honda Civic 2018")
                .price(5127.53)
                .condition(Condition.LIKE_NEW)
                .build();

        Item secondItem = Item.builder()
                .itemName("Toyota Camry 2015")
                .price(3549.19)
                .condition(Condition.LIKE_NEW)
                .build();

        Item thirdItem = Item.builder()
                .itemName("Chevrolet Camaro 2019")
                .category(Category.VEHICLES)
                .price(2301.89)
                .condition(Condition.LIKE_NEW)
                .build();


        when(modelMapper.map(firstItem, ItemInfo.class)).thenReturn(ItemInfo.builder()
                .itemName(firstItem.getItemName())
                .price(firstItem.getPrice())
                .condition(firstItem.getCondition())
                .build());

        when(modelMapper.map(secondItem, ItemInfo.class)).thenReturn(ItemInfo.builder()
                .itemName(secondItem.getItemName())
                .price(secondItem.getPrice())
                .condition(secondItem.getCondition())
                .build());

        when(modelMapper.map(thirdItem, ItemInfo.class)).thenReturn(ItemInfo.builder()
                .itemName(thirdItem.getItemName())
                .price(thirdItem.getPrice())
                .condition(thirdItem.getCondition())
                .build());

        doReturn(List.of(secondItem, thirdItem)).when(itemListingRepository).findFilteredItems(null, Category.VEHICLES, Condition.LIKE_NEW, 1, 9999999, 2000.00, 4000.00, false);

        // Act
        List<ItemInfo> result = itemListingService.getFilteredItems("", "vehicles", "like_new", 1, 9999999, 2000.00, 4000.00, false);

        // Assert
        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(result)
                .extracting(ItemInfo::getPrice)
                .allMatch(price -> price >= 2000.00 && price <= 4000.00);

        Assertions.assertThat(result)
                .extracting(ItemInfo::getCondition)
                .allMatch(condition -> condition.equals(Condition.LIKE_NEW));

        // Verify
        verify(itemListingRepository, times(1)).findFilteredItems(null, Category.VEHICLES, Condition.LIKE_NEW, 1, 9999999, 2000.00, 4000.00, false);
        verify(modelMapper, times(1)).map(secondItem, ItemInfo.class);
        verify(modelMapper, times(1)).map(thirdItem, ItemInfo.class);

    }

    @Test
    void shouldReturnFilteredItemsWithNullField() {
        Item firstItem = Item.builder()
                .itemName("Honda Civic 2018")
                .price(5127.53)
                .quantity(1)
                .condition(Condition.LIKE_NEW)
                .category(Category.VEHICLES)
                .featured(false)
                .build();

        Item secondItem = Item.builder()
                .itemName("Toyota Camry 2015")
                .price(3549.19)
                .quantity(1)
                .condition(Condition.LIKE_NEW)
                .category(Category.VEHICLES)
                .featured(false)
                .build();

        Item thirdItem = Item.builder()
                .itemName("Chevrolet Camaro 2019")
                .price(2301.89)
                .quantity(1)
                .condition(Condition.LIKE_NEW)
                .category(Category.VEHICLES)
                .featured(false)
                .build();

        doReturn(List.of(firstItem, secondItem, thirdItem)).when(itemListingRepository).findFilteredItems(null, Category.VEHICLES, null, 1, 9999999, 2000.00, 10000.00, false);

        when(modelMapper.map(firstItem, ItemInfo.class)).thenReturn(ItemInfo.builder()
                .itemName(firstItem.getItemName())
                .price(firstItem.getPrice())
                .quantity(firstItem.getQuantity())
                .condition(firstItem.getCondition())
                .category(firstItem.getCategory())
                .featured(firstItem.isFeatured())
                .build());

        when(modelMapper.map(secondItem, ItemInfo.class)).thenReturn(ItemInfo.builder()
                .itemName(secondItem.getItemName())
                .price(secondItem.getPrice())
                .quantity(secondItem.getQuantity())
                .condition(secondItem.getCondition())
                .category(secondItem.getCategory())
                .featured(secondItem.isFeatured())
                .build());

        when(modelMapper.map(thirdItem, ItemInfo.class)).thenReturn(ItemInfo.builder()
                .itemName(thirdItem.getItemName())
                .price(thirdItem.getPrice())
                .quantity(thirdItem.getQuantity())
                .condition(thirdItem.getCondition())
                .category(thirdItem.getCategory())
                .featured(thirdItem.isFeatured())
                .build());

        // Act
        List<ItemInfo> result = itemListingService.getFilteredItems("", "vehicles", "", 1, 9999999, 2000.00, 10000.00, false);

        // Assert
        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(3);

        Assertions.assertThat(result)
                .extracting(ItemInfo::getPrice)
                .allMatch(price -> price >= 0 && price <= 10000.00);

        Assertions.assertThat(result)
                .extracting(ItemInfo::getCategory)
                .allMatch(category -> category.equals(Category.VEHICLES));

        Assertions.assertThat(result)
                .extracting(ItemInfo::getCondition)
                .allMatch(condition -> condition.equals(Condition.LIKE_NEW));

        Assertions.assertThat(result)
                .extracting(ItemInfo::getQuantity)
                .allMatch(quantity -> quantity >= 1 && quantity <= 9999999);

        Assertions.assertThat(result)
                .extracting(ItemInfo::isFeatured)
                .allMatch(featured -> !featured);


        // Verify
        verify(itemListingRepository, times(1)).findFilteredItems(null, Category.VEHICLES, null, 1, 9999999, 2000.00, 10000.00, false);
        verify(modelMapper, times(1)).map(firstItem, ItemInfo.class);
        verify(modelMapper, times(1)).map(secondItem, ItemInfo.class);
        verify(modelMapper, times(1)).map(thirdItem, ItemInfo.class);

    }

    @Test
    void shouldConvertStringToCategory() {
        // Arrange
        String categoryString = "vehicles";

        // Act
        Category result = itemListingService.convertStringToCategory(categoryString);

        // Assert
        Assertions.assertThat(result).isEqualTo(Category.VEHICLES);
    }

    @Test
    void shouldConvertStringToCondition() {
        // Arrange
        String conditionString = "like_new";

        // Act
        Condition result = itemListingService.convertStringToCondition(conditionString);

        // Assert
        Assertions.assertThat(result).isEqualTo(Condition.LIKE_NEW);
    }

    @Test
    void shouldAssignItemNameNull() {
        // Arrange
        String itemName = "";

        // Act
        String result = itemListingService.assignItemNameOrNull(itemName);

        // Assert
        Assertions.assertThat(result).isNull();
    }

    @Test
    void shouldAssignItemName() {
        // Arrange
        String itemName = "laptop";

        // Act
        String result = itemListingService.assignItemNameOrNull(itemName);

        // Assert
        Assertions.assertThat(result).isEqualTo(itemName);
    }

    @Test
    void shouldReturnListWithItemsFromSeller() {
        // Arrange
        Item firstItem = Item.builder()
                .seller("seller")
                .build();

        Item secondItem = Item.builder()
                .seller("seller")
                .build();

        Item thirdItem = Item.builder()
                .seller("another seller")
                .build();

        when(itemListingRepository.findItemsBySeller("seller")).thenReturn(List.of(firstItem, secondItem));
        when(modelMapper.map(firstItem, ItemInfo.class)).thenReturn(ItemInfo.builder().seller("seller").build());
        when(modelMapper.map(secondItem, ItemInfo.class)).thenReturn(ItemInfo.builder().seller("seller").build());

        // Act
        List<ItemInfo> result = itemListingService.getItemsBySeller("seller");

        // Assert
        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(result)
                .extracting(ItemInfo::getSeller)
                .allMatch(sellerName -> sellerName.equals("seller"));

        Assertions.assertThat(result)
                .extracting(ItemInfo::getClass)
                .allMatch(ItemInfo ->ItemInfo.equals(ItemInfo.class));

        // Verify
        verify(itemListingRepository, times(1)).findItemsBySeller("seller");
        verify(modelMapper, times(1)).map(firstItem, ItemInfo.class);
        verify(modelMapper, times(1)).map(secondItem, ItemInfo.class);
        verify(modelMapper, times(0)).map(thirdItem, ItemInfo.class);
    }

    @Test
    void shouldUpdateItemQuantity() {
        // Arrange
        Item item = Item.builder()
                .itemId(UUID.randomUUID().toString())
                .quantity(10)
                .build();

        when(itemListingRepository.findItemByItemId(item.getItemId())).thenReturn(Optional.of(item));
        when(itemListingRepository.save(item)).thenReturn(item);

        // Act
        itemListingService.updateItemQuantity(item.getItemId(), 5);

        // Assert
        Assertions.assertThat(item.getQuantity()).isEqualTo(5);

        // Verify
        verify(itemListingRepository, times(1)).findItemByItemId(item.getItemId());
        verify(itemListingRepository, times(1)).save(item);

    }

}