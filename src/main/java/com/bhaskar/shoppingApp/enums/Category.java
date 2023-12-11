package com.bhaskar.shoppingApp.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Category {
    MOBILE("MOBILE", "Mobile"),
    ELECTRONICS("ELECTRONICS", "Electronics"),
    FASHION("FASHION", "Fashion"),
    SPORTS("SPORTS", "Sports"),
    EATABLES("EATABLES", "Eatables"),
    COMPUTERS("COMPUTERS", "Computers & Accessories"),
    BOOKS("BOOKS", "Books"),
    BEAUTY_AND_PERSONAL_CARE("BEAUTY_AND_PERSONAL_CARE", "Beauty & Personal care"),
    HOME_AND_KITCHEN("HOME_AND_KITCHEN", "Home & Kitchen");

    private final String key;
    private final String name;

    Category(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    private static Map<String, Category> FORMAT_MAP = Stream
            .of(Category.values())
            .collect(Collectors.toMap(s -> s.key, Function.identity()));

    @JsonCreator // This is the factory method and must be static
    public static Category fromString(String key) {
        return Optional
                .ofNullable(FORMAT_MAP.get(key))
                .orElseThrow(() -> new IllegalArgumentException(key));
    }
}
