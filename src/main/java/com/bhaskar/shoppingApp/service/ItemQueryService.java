package com.bhaskar.shoppingApp.service;

import com.bhaskar.shoppingApp.dto.ItemLiteDto;
import com.bhaskar.shoppingApp.entity.Item;
import com.bhaskar.shoppingApp.payload.Filter;
import com.bhaskar.shoppingApp.query.ItemQuery;
import com.bhaskar.shoppingApp.repositories.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemQueryService {

    private final ItemQuery itemQuery;

    private final MongoTemplate mongoTemplate;

    private final ObjectMapper objectMapper;

    private final ItemRepository itemRepository;

    @Inject
    public ItemQueryService(ItemQuery itemQuery, MongoTemplate mongoTemplate, ObjectMapper objectMapper, ItemRepository itemRepository) {
        this.itemQuery = itemQuery;
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper;
        this.itemRepository = itemRepository;
    }

    public List<ItemLiteDto> getItemsForCategory(String categoryId){
        MongoCollection<Document> itemsCollection = mongoTemplate.getCollection("items");
        List<Document> pipelineQuery = itemQuery.queryLiteItemsByCategory(categoryId);
        List<Document> documents = itemsCollection.aggregate(pipelineQuery).into(new ArrayList<>());
        List<ItemLiteDto> itemLiteDtos = documents.stream().map(document -> objectMapper.convertValue(document, ItemLiteDto.class)).collect(Collectors.toList());
        return itemLiteDtos;
    }

    public List<ItemLiteDto> getItemsWithFilter(Filter filter){
        MongoCollection<Document> itemsCollection = mongoTemplate.getCollection("items");
        List<Document> pipelineQuery = itemQuery.queryLiteItemsByFilter(filter);
        List<Document> documents = itemsCollection.aggregate(pipelineQuery).into(new ArrayList<>());
        List<ItemLiteDto> itemLiteDtos = documents.stream().map(document -> objectMapper.convertValue(document, ItemLiteDto.class)).collect(Collectors.toList());
        return itemLiteDtos;
    }

    public Item getItemById(String id){
        return itemRepository.getItemById(id);
    }

    public Optional<Document> getItemCombinationIds(String itemId){
        MongoCollection<Document> itemsCollection = mongoTemplate.getCollection("items");
        List<Document> pipelineQuery = itemQuery.queryCombinationIdsById(itemId);
        List<Document> documents = itemsCollection.aggregate(pipelineQuery).into(new ArrayList<>());
        Optional<Document> itemCombinationIds = documents.stream().findFirst();
        return itemCombinationIds;
    }
}
