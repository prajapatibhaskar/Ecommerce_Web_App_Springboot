package com.bhaskar.shoppingApp.repositories;

import com.bhaskar.shoppingApp.entity.Credential;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
public interface UserRepository extends MongoRepository<Credential, String> {
    Credential findByUsername(String username);
    Credential findByUsernameAndPassword(String username, String password);
}
