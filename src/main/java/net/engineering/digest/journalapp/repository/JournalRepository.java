package net.engineering.digest.journalapp.repository;

import net.engineering.digest.journalapp.entity.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends MongoRepository<JournalEntries, ObjectId> {
}
