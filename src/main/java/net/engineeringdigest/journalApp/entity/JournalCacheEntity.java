package net.engineeringdigest.journalApp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "journal_cache_entity")
public class JournalCacheEntity {

    @Id
    private ObjectId journalId;
    private String cacheId;
    private String cacheData;


}
