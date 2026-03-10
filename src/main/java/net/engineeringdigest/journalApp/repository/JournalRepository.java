package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends MongoRepository<JournalEntries, ObjectId> {
}
