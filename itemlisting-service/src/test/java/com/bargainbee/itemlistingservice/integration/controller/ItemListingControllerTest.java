package com.bargainbee.itemlistingservice.integration.controller;

import com.bargainbee.itemlistingservice.model.dto.ItemInfo;
import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemRequest;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.bargainbee.itemlistingservice.model.entity.Item;
import com.bargainbee.itemlistingservice.repository.ItemListingRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ItemListingControllerTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11")
            .withUsername("postgres")
            .withPassword("1111")
            .withDatabaseName("bargainbee_db")
            .withExposedPorts(5432);

    static {
        postgreSQLContainer.start();
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItemListingRepository itemListingRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @AfterEach
    void afterEach() {
        itemListingRepository.deleteAll();
        itemListingRepository.resetSequence();
    }


    /* Helper Methods */
    //

    private NewItemRequest getNewItemRequest(Category category, Condition condition) {
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

    private NewItemRequest getNewItemRequest(String itemName) {
        return NewItemRequest.builder()
                .itemName(itemName)
                .description("testDescription")
                .price(100.0)
                .quantity(10)
                .category(Category.ELECTRONICS)
                .condition(Condition.NEW)
                .image("testImage")
                .tags("testTags")
                .build();
    }

    private ItemUpdatedDto getItemUpdatedDto() {
        return ItemUpdatedDto.builder()
                .itemName("newUpdatedItem")
                .description("testNewUpdatedItem")
                .price(200.0)
                .quantity(100)
                .category(Category.BOOKS)
                .condition(Condition.DEFECTIVE)
                .image("testNewUpdatedImage")
                .tags("testNewUpdatedTags")
                .build();
    }

    /**
     * Helper method to create three items in the database with category  and condition
     * for testing purposes only
     *
     * @throws Exception - Exception thrown if the POST request fails
     */
    private void createThreeItemsSameCategory(Category category, Condition condition) throws Exception {
        NewItemRequest firstItemRequest = getNewItemRequest(category, condition);
        NewItemRequest secondItemRequest = getNewItemRequest(category, condition);
        NewItemRequest thirdItemRequest = getNewItemRequest(category, condition);

        performCreateNewItem(firstItemRequest);
        performCreateNewItem(secondItemRequest);
        performCreateNewItem(thirdItemRequest);
    }

    private void createThreeItems() throws Exception {
        NewItemRequest firstItemRequest = getNewItemRequest(Category.BOOKS, Condition.REFURBISHED);
        NewItemRequest secondItemRequest = getNewItemRequest(Category.CLOTHING, Condition.NEW);
        NewItemRequest thirdItemRequest = getNewItemRequest(Category.FURNITURE, Condition.NEW);

        performCreateNewItem(firstItemRequest);
        performCreateNewItem(secondItemRequest);
        performCreateNewItem(thirdItemRequest);
    }

    private ItemInfo createItemByItemName(String itemName) throws Exception {
        String responseString = performCreateNewItem(getNewItemRequest(itemName));

        return objectMapper.readValue(responseString, ItemInfo.class);
    }

    /**
     * Helper method to perform a POST request to create a new item
     *
     * @param newItemRequest - DTO containing the new item information
     * @throws Exception - Exception thrown if the POST request fails
     */
    private String performCreateNewItem(NewItemRequest newItemRequest) throws Exception {
        String newItemRequestString = objectMapper.writeValueAsString(newItemRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/item/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newItemRequestString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.itemId").exists())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    /**
     * Helper method to perform a PUT request to update an existing item
     *
     * @param itemUpdatedDto - DTO containing the new item information
     * @param itemToUpdate   - Item to be updated
     * @throws Exception - Exception thrown if the PUT request fails
     */
    private void performUpdateItem(ItemUpdatedDto itemUpdatedDto, Item itemToUpdate) throws Exception {
        String itemUpdatedRequestString = objectMapper.writeValueAsString(itemUpdatedDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/item/update/" + itemToUpdate.getItemId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemUpdatedRequestString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").exists());
    }

    /**
     * Helper method to perform a DELETE request to delete an existing item
     *
     * @param itemToDelete - Item to be deleted
     * @throws Exception - Exception thrown if the DELETE request fails
     */
    private void performDeleteItem(Item itemToDelete) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/item/delete/" + itemToDelete.getItemId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private String performGetItemsByCategory() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/item/category/" + "electronics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    private String performGetFeaturedItems() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/item/featured")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    private String performGetRelatedItems(Item item) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/item/related/" + item.getItemId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    private String performSearchItemsByKeyword(String itemName) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/item/search?item-name=" + itemName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    private String performGetAllItems() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/item/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }


    /* Tests */
    //

    @Test
    void shouldCreateNewItemSuccessfully() throws Exception {
        // Arrange
        NewItemRequest newItemRequest = getNewItemRequest(Category.ELECTRONICS, Condition.NEW);

        // Act
        ItemInfo newItem = createItemByItemName(newItemRequest.getItemName());

        // Assert
        Assertions.assertThat(itemListingRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(newItem).isInstanceOf(ItemInfo.class);
        Assertions.assertThat(newItem.getItemName()).isEqualTo(newItemRequest.getItemName());
        Assertions.assertThat(newItem.getItemId()).isNotNull();
    }

    @Test
    void shouldUpdateExistingItemSuccessfully() throws Exception {
        // Arrange
        NewItemRequest newItemRequest = getNewItemRequest(Category.ELECTRONICS, Condition.NEW);
        performCreateNewItem(newItemRequest);
        Item oldItem = itemListingRepository.findById(1L).get();

        // Act
        ItemUpdatedDto itemUpdatedDto = getItemUpdatedDto();
        performUpdateItem(itemUpdatedDto, oldItem);
        Item updatedItem = itemListingRepository.findById(1L).get();

        // Assert
        Assertions.assertThat(itemListingRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(updatedItem).isNotNull();
        Assertions.assertThat(updatedItem.getId()).isInstanceOf(Long.class);
        Assertions.assertThat(updatedItem).isInstanceOf(Item.class);
        Assertions.assertThat(oldItem).isNotEqualTo(updatedItem);
    }

    @Test
    void shouldDeleteItemSuccessfully() throws Exception {
        // Arrange
        NewItemRequest newItemRequest = getNewItemRequest(Category.ELECTRONICS, Condition.NEW);
        performCreateNewItem(newItemRequest);
        Item itemToDelete = itemListingRepository.findById(1L).get();

        // Act
        performDeleteItem(itemToDelete);

        // Assert
        Assertions.assertThat(itemListingRepository.findAll().size()).isEqualTo(0);
        Assertions.assertThat(itemListingRepository.findById(1L).isEmpty()).isTrue();
    }

    @Test
    void shouldReturnAllItemsByCategorySpecificCategory() throws Exception {
        // Arrange
        createThreeItemsSameCategory(Category.ELECTRONICS, Condition.NEW);

        // Act
        String responseContent = performGetItemsByCategory();
        List<ItemInfo> items = objectMapper.readValue(responseContent, new TypeReference<List<ItemInfo>>() {
        });

        // Assert
        Assertions.assertThat(items)
                .hasSize(3)
                .extracting(ItemInfo::getCategory)
                .contains(Category.ELECTRONICS);

        Assertions.assertThat(items)
                .extracting(ItemInfo::getClass)
                .allMatch(itemClass -> itemClass.equals(ItemInfo.class));
    }

    @Test
    void shouldReturnAllItemsByCategoryIsNotNull() throws Exception {
        // Arrange
        createThreeItemsSameCategory(Category.ELECTRONICS, Condition.NEW);

        // Act
        String responseContent = performGetItemsByCategory();
        List<ItemInfo> items = objectMapper.readValue(responseContent, new TypeReference<List<ItemInfo>>() {
        });

        // Assert
        Assertions.assertThat(items)
                .isNotNull();

        Assertions.assertThat(items)
                .extracting(ItemInfo::getClass)
                .allMatch(itemClass -> itemClass.equals(ItemInfo.class));
    }

    @Test
    void shouldReturnAllItemsByCategoryIsNotEmpty() throws Exception {
        // Arrange
        createThreeItemsSameCategory(Category.ELECTRONICS, Condition.NEW);

        // Act
        String responseContent = performGetItemsByCategory();
        List<ItemInfo> items = objectMapper.readValue(responseContent, new TypeReference<List<ItemInfo>>() {
        });

        // Assert
        Assertions.assertThat(items)
                .isNotEmpty();

        Assertions.assertThat(items)
                .extracting(ItemInfo::getClass)
                .allMatch(itemClass -> itemClass.equals(ItemInfo.class));
    }

    @Test
    void shouldReturnFeaturedItemSuccessfully() throws Exception {
        // Arrange
        Item item = Item.builder()
                .itemName("testItem")
                .description("testDescription")
                .price(100.0)
                .quantity(10)
                .category(Category.ELECTRONICS)
                .condition(Condition.NEW)
                .image("testImage")
                .tags("testTags")
                .featured(true)
                .build();

        itemListingRepository.save(item);

        // Act
        String responseContent = performGetFeaturedItems();
        List<ItemInfo> featuredItems = objectMapper.readValue(responseContent, new TypeReference<List<ItemInfo>>() {
        });

        // Assert
        Assertions.assertThat(featuredItems)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .extracting(ItemInfo::getClass)
                .allMatch(itemClass -> itemClass.equals(ItemInfo.class));

        Assertions.assertThat(featuredItems)
                .extracting(ItemInfo::isFeatured)
                .contains(true);
    }

    @Test
    void shouldReturnThreeRelatedItems() throws Exception {
        // Arrange
        performCreateNewItem(getNewItemRequest(Category.ELECTRONICS, Condition.NEW));
        Item item = itemListingRepository.findById(1L)
                .orElseThrow(RuntimeException::new);

        createThreeItemsSameCategory(Category.ELECTRONICS, Condition.NEW);

        // Act
        String responseContent = performGetRelatedItems(item);
        List<ItemInfo> items = objectMapper.readValue(responseContent, new TypeReference<List<ItemInfo>>() {
        });

        // Assert
        Assertions.assertThat(items)
                .hasSize(3)
                .isNotNull()
                .isNotEmpty()
                .extracting(ItemInfo::getClass)
                .allMatch(itemClass -> itemClass.equals(ItemInfo.class));

        Assertions.assertThat(items)
                .extracting(ItemInfo::getCategory)
                .contains(Category.ELECTRONICS);
    }

    @Test
    void shouldReturnItemsMatchingKeyword() throws Exception {
        // Arrange
        ItemInfo firstItem = createItemByItemName("Asus laptop");
        ItemInfo secondItem = createItemByItemName("Laptop NS xf v3");
        ItemInfo thirdItem = createItemByItemName("Big book");
        ItemInfo fourthItem = createItemByItemName("lap top");

        // Act
        String responseContent = performSearchItemsByKeyword("laptop");
        List<ItemInfo> items = objectMapper.readValue(responseContent, new TypeReference<List<ItemInfo>>() {
        });

        // Assert
        Assertions.assertThat(items.size()).isEqualTo(2);

        Assertions.assertThat(items)
                .extracting(ItemInfo::getItemName)
                .contains(firstItem.getItemName(), secondItem.getItemName());

        Assertions.assertThat(items)
                .extracting(ItemInfo::getItemName)
                .doesNotContain(thirdItem.getItemName(), fourthItem.getItemName());

        Assertions.assertThat(items)
                .extracting(ItemInfo::getClass)
                .allMatch(itemClass -> itemClass.equals(ItemInfo.class));

        Assertions.assertThat(items)
                .extracting(ItemInfo::getItemName)
                .allMatch(itemName -> itemName.toLowerCase().contains("laptop"));
    }

    @Test
    void shouldReturnAllItems() throws Exception {
        // Arrange
        ItemInfo firstItem = createItemByItemName("Test Item 1");
        ItemInfo secondItem = createItemByItemName("Test Item 2");
        ItemInfo thirdItem = createItemByItemName("Test Item 3");

        // Act
        String responseContent = performGetAllItems();
        List<ItemInfo> items = objectMapper.readValue(responseContent, new TypeReference<List<ItemInfo>>() {
        });

        // Assert
        Assertions.assertThat(items)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .extracting(ItemInfo::getClass)
                .allMatch(itemClass -> itemClass.equals(ItemInfo.class));
    }

}