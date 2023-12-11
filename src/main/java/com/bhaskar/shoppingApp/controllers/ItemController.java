package com.bhaskar.shoppingApp.controllers;

import com.bhaskar.shoppingApp.dto.ItemLiteDto;
import com.bhaskar.shoppingApp.entity.Item;
import com.bhaskar.shoppingApp.payload.Filter;
import com.bhaskar.shoppingApp.repositories.ItemRepository;
import com.bhaskar.shoppingApp.service.ItemQueryService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000/")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemQueryService itemQueryService;

    @PostMapping("/item")
    public Item addItem(@RequestBody Item item){

        return itemRepository.save(item);
    }

    @GetMapping("/category/{categoryId}/items")
    public List<ItemLiteDto> getItemsForCategory(@PathVariable String categoryId){
        return itemQueryService.getItemsForCategory(categoryId);
    }

    @PostMapping("/items")
    public List<ItemLiteDto> getItems(@RequestBody Filter filter){
        return itemQueryService.getItemsWithFilter(filter);
    }

    @GetMapping("/item/{id}")
    public Item getItem(@PathVariable String id){
        return itemQueryService.getItemById(id);
    }

    @GetMapping("/item/{itemId}/combinations")
    public Optional<Document> getItemCombinationIds(@PathVariable String itemId){
            return itemQueryService.getItemCombinationIds(itemId);
    }


}
