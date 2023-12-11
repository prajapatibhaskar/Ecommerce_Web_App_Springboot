package com.bhaskar.shoppingApp.repositories;

import com.bhaskar.shoppingApp.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
    Item getItemById(String id);
}
