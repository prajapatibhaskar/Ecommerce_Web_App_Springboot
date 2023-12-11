package com.bhaskar.shoppingApp.query;

import com.bhaskar.shoppingApp.payload.Filter;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class ItemQuery {
    public List<Document> queryLiteItemsByCategory(String categoryId) {
        return Arrays.asList(new Document("$redact",
                        new Document("$cond", Arrays.asList(new Document("$setIsSubset", Arrays.asList(Arrays.asList(categoryId), "$categories")), "$$KEEP", "$$PRUNE"))),
                new Document("$project",
                        new Document("name", 1L)
                                .append("brand", 1L)
                                .append("price",
                                        new Document("$arrayElemAt", Arrays.asList("$optionsInfo.optionCombinations.price", 0L)))
                                .append("id",
                                        new Document("$toString", "$_id"))));
    }

    public List<Document> queryLiteItemsByFilter(Filter filter){
        List<Document> aggregationPipeline = new ArrayList<>();

        //Stage 1 -> Filter by Category
        if(Objects.nonNull(filter.getCategory())){
            aggregationPipeline.add(new Document("$redact",
                    new Document("$cond", Arrays.asList(new Document("$setIsSubset", Arrays.asList(Arrays.asList(filter.getCategory()), "$categories")), "$$KEEP", "$$PRUNE"))));
        }

        aggregationPipeline.add(new Document("$project",
                new Document("name", 1L)
                        .append("brand", 1L)
                        .append("price",
                                new Document("$arrayElemAt", Arrays.asList("$optionsInfo.optionCombinations.price", 0L)))
                        .append("id",
                                new Document("$toString", "$_id"))
                        .append("reviews.rating", 1L)
                        .append("optionsInfo.optionCombinations", 1L)));

        aggregationPipeline.add(new Document("$addFields",
                new Document("averageRating",
                        new Document("$avg", "$reviews.rating"))));

        aggregationPipeline.add(new Document("$unwind",
                new Document("path", "$optionsInfo.optionCombinations")
                        .append("includeArrayIndex", "string")
                        .append("preserveNullAndEmptyArrays", true)));

        aggregationPipeline.add(new Document("$project",
                new Document("price", 0L)));

        aggregationPipeline.add(new Document("$project",
                new Document("price", "$optionsInfo.optionCombinations.price")
                        .append("name", 1L)
                        .append("brand", 1L)
                        .append("id", 1L)
                        .append("averageRating", 1L)
                        .append("combinationId", "$optionsInfo.optionCombinations.cid")));

        aggregationPipeline.add(
                new Document("$group",
                    new Document("_id",
                            new Document("id", "$id")
                                    .append("name", "$name")
                                    .append("brand", "$brand")
                                    .append("averageRating", "$averageRating"))
                            .append("prices",
                                    new Document("$push", "$price"))
                            .append("combinationIds",
                                    new Document("$push", "$combinationId")))
        );

        aggregationPipeline.add(new Document("$project",
                new Document("_id", 0L)
                        .append("id", "$_id.id")
                        .append("name", "$_id.name")
                        .append("prices", 1L)
                        .append("brand", "$_id.brand")
                        .append("averageRating", "$_id.averageRating")
                        .append("combinationIds", 1L)));

        // Stage 9 -> Filter by price range
        if(Objects.nonNull(filter.getPriceRange())){
            aggregationPipeline.add(new Document("$match",
                    new Document("$expr",
                            new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList(1L, 1L)),
                                    new Document("$gte", Arrays.asList(new Document("$max", "$prices.amount"), filter.getPriceRange().getMin())),
                                    new Document("$lte", Arrays.asList(new Document("$min", "$prices.amount"), filter.getPriceRange().getMax())))))));
        }

        // Stage 10 -> Filter by minimum rating
        if(Objects.nonNull(filter.getMinimumRating())){
            aggregationPipeline.add(new Document("$match",
                    new Document("$expr",
                            new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList(1L, 1L)),
                                    new Document("$gte", Arrays.asList(new Document("$max", "$averageRating"), filter.getMinimumRating())),
                                    new Document("$lte", Arrays.asList(new Document("$min", "$averageRating"), 5L)))))));
        }

        aggregationPipeline.add(new Document("$sort", new Document("id", 1L)));

        return aggregationPipeline;
    }

    public List<Document> queryCombinationIdsById(String itemId){
        return Arrays.asList(new Document("$match",
                        new Document("_id",
                                new ObjectId(itemId))),
                new Document("$unwind",
                        new Document("path", "$optionsInfo.optionCombinations")
                                .append("includeArrayIndex", "string")
                                .append("preserveNullAndEmptyArrays", true)),
                new Document("$project",
                        new Document("combinationId", "$optionsInfo.optionCombinations.cid")
                                .append("id",
                                        new Document("$toString", "$_id"))),
                new Document("$group",
                        new Document("_id",
                                new Document("id", "$id"))
                                .append("combinationIds",
                                        new Document("$push", "$combinationId"))),
                new Document("$project",
                        new Document("combinationIds", 1L)
                                .append("_id", 0L)));
    }

}
