package com.landmark.app;

import com.landmark.app.model.domain.user.User;
import com.landmark.app.model.repository.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Ignore
    public void saveTest() {
        try {
            redisTemplate.opsForValue().set("test1", "1234");
            redisTemplate.expire("test1", 1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void userTest(){
        String username = "test1234";
        User user = userRepository.findByUsername(username).get();
        System.out.println(user.getRole().getRolename());
    }

}
