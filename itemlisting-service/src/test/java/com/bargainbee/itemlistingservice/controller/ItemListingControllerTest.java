package com.bargainbee.itemlistingservice.controller;

import com.bargainbee.itemlistingservice.model.dto.ItemUpdatedDto;
import com.bargainbee.itemlistingservice.model.dto.NewItemRequest;
import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.bargainbee.itemlistingservice.model.entity.Item;
import com.bargainbee.itemlistingservice.repository.ItemListingRepository;
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

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItemListingRepository itemListingRepository;

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @AfterEach
    void afterEach() {
        itemListingRepository.deleteAll();
        itemListingRepository.resetSequence();
    }

    private NewItemRequest getNewItemRequest() {
        return NewItemRequest.builder()
                .itemName("testItem")
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
     * Helper method to perform a POST request to create a new item
     *
     * @param newItemRequest - DTO containing the new item information
     * @throws Exception - Exception thrown if the POST request fails
     */
    private void performCreateNewItem(NewItemRequest newItemRequest) throws Exception {
        String newItemRequestString = objectMapper.writeValueAsString(newItemRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/item/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newItemRequestString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.itemId").exists());
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

    private void performDeleteItem(Item itemToDelete) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/item/delete/" + itemToDelete.getItemId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldCreateNewItemSuccessfully() throws Exception {
        // Arrange
        NewItemRequest newItemRequest = getNewItemRequest();

        // Act
        performCreateNewItem(newItemRequest);
        Item newItem = itemListingRepository.findById(1L).get();

        // Assert
        Assertions.assertThat(itemListingRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(newItem).isInstanceOf(Item.class);
        Assertions.assertThat(newItem.getItemName()).isEqualTo(newItemRequest.getItemName());
        Assertions.assertThat(newItem.getId()).isNotNull();
        Assertions.assertThat(newItem.getId()).isInstanceOf(Long.class);

    }

    @Test
    void shouldUpdateExistingItemSuccessfully() throws Exception {
        // Arrange
        NewItemRequest newItemRequest = getNewItemRequest();
        performCreateNewItem(newItemRequest);
        int size = itemListingRepository.findAll().size();
        List<Item> items = itemListingRepository.findAll();
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
        NewItemRequest newItemRequest = getNewItemRequest();
        performCreateNewItem(newItemRequest);
        Item itemToDelete = itemListingRepository.findById(1L).get();

        // Act
        performDeleteItem(itemToDelete);

        // Assert
        Assertions.assertThat(itemListingRepository.findAll().size()).isEqualTo(0);
        Assertions.assertThat(itemListingRepository.findById(1L).isEmpty()).isTrue();

    }

}