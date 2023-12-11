package com.bhaskar.shoppingApp.dao;

import com.bhaskar.shoppingApp.dto.CurrencyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionCombination {
    HashMap<String /* option_category_name */, String /* option_id */> combination;
    CurrencyDto price;
    int quantity;
    Map<String, String> details;
    String cid;
}
