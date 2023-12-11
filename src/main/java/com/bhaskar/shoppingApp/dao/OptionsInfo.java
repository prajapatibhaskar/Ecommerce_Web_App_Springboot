package com.bhaskar.shoppingApp.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionsInfo {
    List<OptionCategory> optionCategories;
    List<OptionCombination> optionCombinations;
}
