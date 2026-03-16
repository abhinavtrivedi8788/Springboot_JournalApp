package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalCacheEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalCacheRepository extends MongoRepository<JournalCacheEntity, ObjectId> {
}
