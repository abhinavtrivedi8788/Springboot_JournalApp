package net.engineering.digest.journalapp.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisCacheService {
    // This class can be used to implement caching logic using Redis.
    // You can add methods to set, get, and delete cache entries as needed.

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T getCacheValue(String key, Class<T> entityClass) {
        try{
            Object value = redisTemplate.opsForValue().get(key);
            if(value != null){
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(value.toString(), entityClass);
            } else {
                log.info("Cache miss for key: {}", key);
                return null;
            }
        }catch (Exception e){
            log.error("Error while getting cache value for key: {}", key, e);
            return null;
        }
    }

    public void setCacheValue(String key, Object value, Long timeToLiveInSeconds) {
        try{
           redisTemplate.opsForValue().set(key, new ObjectMapper().writeValueAsString(value), timeToLiveInSeconds, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("Error while setting cache value for key: {}", key, e);
        }
    }
}
