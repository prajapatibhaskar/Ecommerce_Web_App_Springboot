package com.bhaskar.shoppingApp.entity;

import com.bhaskar.shoppingApp.dao.OptionsInfo;
import com.bhaskar.shoppingApp.dao.Review;
import com.bhaskar.shoppingApp.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    String name;
    String brand;
    List<String> categories;
    List<Review> reviews;
    OptionsInfo optionsInfo;
    List<String> description;
}
