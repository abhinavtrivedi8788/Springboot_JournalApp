package net.engineeringdigest.journalApp.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.repository.JournalCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCache {

    @Autowired
    private JournalCacheRepository journalCacheRepository;

    private Map<String,String> APP_CACHE = new HashMap<>();

    @PostConstruct
    private void loadCache(){
        journalCacheRepository.findAll().forEach(journalCache -> {
            APP_CACHE.put(journalCache.getCacheId(), journalCache.getCacheData());
        });
    }

}
