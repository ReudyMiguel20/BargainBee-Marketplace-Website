package com.reudymiguel.itemlistingservice.repository;

import com.reudymiguel.itemlistingservice.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemListingRepository extends JpaRepository<Item, Long> {

}
