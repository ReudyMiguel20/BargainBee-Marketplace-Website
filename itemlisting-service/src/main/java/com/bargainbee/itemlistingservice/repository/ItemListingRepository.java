package com.bargainbee.itemlistingservice.repository;

import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Condition;
import com.bargainbee.itemlistingservice.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ItemListingRepository extends JpaRepository<Item, Long>,
        PagingAndSortingRepository<Item, Long> {

    List<Item> findItemsByCategory(Category category);

    Optional<Item> findItemByItemId(String itemId);

    @Query("SELECT i FROM Item i WHERE i.featured = true")
    List<Item> findFeaturedItems();

    @Query("SELECT i FROM Item i WHERE i.itemId <> ?1 AND i.category = ?2 ORDER BY RANDOM() LIMIT 3")
    List<Item> findRelatedItems(String itemId, Category category);

    // Reset the id of the table to 1
    @Modifying
    @Query(value = "ALTER SEQUENCE t_items_id_seq RESTART WITH 1", nativeQuery = true)
    void resetSequence();

    List<Item> findItemsByItemNameContainingIgnoreCase(String itemName);

    List<Item> findItemsByPriceBetween(double minPrice, double maxPrice);

    @Query("SELECT i FROM Item i WHERE " +
            "(:category is null OR i.category = :category) " +
            "AND (:condition is null OR i.condition = :condition) " +
            "AND (:itemName is null OR lower(i.itemName) LIKE lower(concat('%', :itemName, '%'))) " +
            "AND (:minQuantity is null OR i.quantity >= :minQuantity) " +
            "AND (:maxQuantity is null OR i.quantity <= :maxQuantity) " +
            "AND i.price BETWEEN :minPrice AND :maxPrice " +
            "AND (:featured is null OR i.featured = :featured)")
    List<Item> findFilteredItems(
            @Param("itemName") String itemName,
            @Param("category") Category category,
            @Param("condition") Condition condition,
            @Param("minQuantity") Integer minQuantity,
            @Param("maxQuantity") Integer maxQuantity,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            @Param("featured") Boolean featured
    );

    List<Item> findItemsBySeller(String seller);
}
