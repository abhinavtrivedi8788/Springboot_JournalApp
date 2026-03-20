package net.engineering.digest.journalapp.repository;

import net.engineering.digest.journalapp.entity.JournalCacheEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalCacheRepository extends MongoRepository<JournalCacheEntity, ObjectId> {
}
