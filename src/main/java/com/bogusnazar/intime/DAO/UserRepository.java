package com.bogusnazar.intime.DAO;

import com.bogusnazar.intime.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByFirstName(String name);

    User findByEmail(@Param("email") String email);
}
