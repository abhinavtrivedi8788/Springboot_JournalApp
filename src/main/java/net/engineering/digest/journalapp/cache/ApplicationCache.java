package net.engineering.digest.journalapp.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.engineering.digest.journalapp.repository.JournalCacheRepository;
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

    private Map<String,String> APP_CACHE ;

    @PostConstruct
    private void loadCache(){
        APP_CACHE = new HashMap<>();
        journalCacheRepository.findAll().forEach(journalCache -> {
            APP_CACHE.put(journalCache.getCacheId(), journalCache.getCacheData());
        });
    }

}
