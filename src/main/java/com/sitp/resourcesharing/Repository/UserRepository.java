package com.sitp.resourcesharing.Repository;

import com.sitp.resourcesharing.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//public interface UserRepository extends MongoRepository<User, String> {
//    User findByName(String name);
//}

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserById(String id);
    User findUserByName(String name);

}