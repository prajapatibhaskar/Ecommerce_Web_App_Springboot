package com.bhaskar.shoppingApp.payload;

import com.bhaskar.shoppingApp.dto.PriceRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    short minimumRating;
    PriceRange priceRange;
    String category;
}
