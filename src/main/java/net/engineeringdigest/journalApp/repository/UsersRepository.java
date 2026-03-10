package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntries;
import net.engineeringdigest.journalApp.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, ObjectId> {

    Users findByUserName(String username);

    void deleteByUserName(String username);
}
