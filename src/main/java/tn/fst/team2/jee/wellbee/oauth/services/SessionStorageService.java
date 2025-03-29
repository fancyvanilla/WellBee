package tn.fst.team2.jee.wellbee.oauth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tn.fst.team2.jee.wellbee.oauth.dto.UserSession;

import java.util.concurrent.TimeUnit;

@Service
public class SessionStorageService {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public SessionStorageService(StringRedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }
    public void setUserSession(String key, UserSession session){
        redisTemplate.opsForValue().set(key, session.getAccessToken());
        redisTemplate.expire(key, session.getExpiresIn(), TimeUnit.SECONDS);
    }

    public UserSession getUserSession(String key){
        String accessToken= redisTemplate.opsForValue().get(key);
        Long expiresIn= redisTemplate.getExpire(key);
        return new UserSession(accessToken, expiresIn);
    }
}
