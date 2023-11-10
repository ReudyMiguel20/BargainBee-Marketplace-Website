package com.bargainbee.itemlistingservice.repository;

import com.bargainbee.itemlistingservice.model.entity.Category;
import com.bargainbee.itemlistingservice.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemListingRepository extends JpaRepository<Item, Long>,
        PagingAndSortingRepository<Item, Long> {

    List<Item> findItemsByCategory(Category category);
    Optional<Item> findItemByItemId(String itemId);

    @Query("SELECT i FROM Item i WHERE i.featured = true")
    List<Item> findFeaturedItems();

    @Query("SELECT i FROM Item i WHERE i.itemId <> ?1 AND i.category = ?2 ORDER BY RANDOM() LIMIT 3")
    List<Item> findRelatedItems(String itemId, Category category);
}
