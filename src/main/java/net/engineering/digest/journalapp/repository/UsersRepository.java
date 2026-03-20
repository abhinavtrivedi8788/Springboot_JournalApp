package net.engineering.digest.journalapp.repository;

import net.engineering.digest.journalapp.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, ObjectId> {

    Users findByUserName(String username);

    void deleteByUserName(String username);
}
