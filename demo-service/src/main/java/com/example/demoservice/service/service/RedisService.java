package com.example.demoservice.service.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Value("${jwt.redis.black.prefix}")
    private String BLACKLIST_PREFIX;

    @Value("${jwt.redis.white.prefix}")
    private String WHITELIST_PREFIX ;

    private final StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.access-token-minute}")
    private int accessMinute ; // JWT 的過期時間（單位：分鐘

    @Value("${jwt.refresh-token-minute}")
    private int refreshMinute; // JWT 的過期時間（單位：分鐘

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // 存值 timeout = 秒
    public void setValue(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    // 存值 永久
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 取值
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 刪除
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }


    /**
     * 將 JWT 加入白名單
     */
    public void addToWhiteList(String account ,String msg) {
        String key = WHITELIST_PREFIX + account;
        redisTemplate.opsForValue().set(key, msg, refreshMinute, TimeUnit.MINUTES);
    }

    /**
     *  檢查 JWT 是否在白名單中
     * @param account 帳號
     * @return boolean
     */
    public boolean isWhiteListed(String account) {
        String key = WHITELIST_PREFIX + account;
        //TODO hasKey有效能問題，須改用其他方式
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 依照帳號，刪除白名單
     * @param account 帳號
     */
    public void deleteWhiteList(String account) {
        redisTemplate.delete( WHITELIST_PREFIX + account);
    }

    /**
     * 將 JWT 加入黑名單
     */
    public void addToBlackList(String jti ,String msg) {
        String key = BLACKLIST_PREFIX + jti;
        //黑名單時間應該等同JWT效期(accessMinute)就夠了
        //但是目前設計，過期的token可以做refresh，所以要延長黑單時間，改用refreshMinute
        redisTemplate.opsForValue().set(key, msg, refreshMinute, TimeUnit.MINUTES);
    }

    /**
     * 檢查 JWT 是否在黑名單中
     * @param jti jwt 唯一識別符
     * @return boolean
     */
    public boolean isBlackListed(String jti) {
        String key = BLACKLIST_PREFIX + jti;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 查所有TOKEN白名單
     * @return Set<String>
     */
    public Set<String> getAllWhiteListedJti() {
        return redisTemplate.keys(WHITELIST_PREFIX + "*");
    }

    /**
     * 查所有TOKEN黑名單
     * @return Set<String>
     */
    public Set<String> getAllBlackListedJti() {
        return redisTemplate.keys(BLACKLIST_PREFIX + "*");
    }
}
