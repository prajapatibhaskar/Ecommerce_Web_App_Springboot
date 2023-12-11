package com.bhaskar.shoppingApp.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

@Data
@Document(collection = "users")
@Entity
public class Credential {
    private String username;
    private String password;
}
