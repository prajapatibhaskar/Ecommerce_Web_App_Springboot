package com.bhaskar.shoppingApp.dto;

import com.bhaskar.shoppingApp.dao.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemLiteDto {
    String name;
    String id;
    String brand;
    float averageRating;
    List<CurrencyDto> prices;
    List<String> combinationIds;
}
