package me.fulln.tuc.config;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;

public class TwoLevelCacheManager extends RedisCacheManager {

	public static final String TOPIC_NAME = "redis_two_level_cache";

	RedisTemplate redisTemplate;

	public TwoLevelCacheManager(RedisTemplate template,RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
		super(cacheWriter, defaultCacheConfiguration);
		this.redisTemplate=template;
	}

	public void publishMessage(String cacheName){
		this.redisTemplate.convertAndSend(TOPIC_NAME,cacheName);
	}

	public void receiver(String name){

	}

}
