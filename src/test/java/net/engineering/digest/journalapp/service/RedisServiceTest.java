package net.engineering.digest.journalapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisTemplate <String,String> redisTemplate;

    @Test
    void testRedisTemplate() {
        // Example of setting a value in Redis
        redisTemplate.opsForValue().set("user", "LatestTestUser");
        redisTemplate.opsForValue().set("email","Test@gmail.com");

        // Example of retrieving a value from Redis
        String value = (String) redisTemplate.opsForValue().get("user");

        // Assert that the value is correct (using JUnit or any assertion library)
        assertEquals("LatestTestUser", value);
    }


}
