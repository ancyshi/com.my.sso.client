package com.my.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.my.dao.CookieIdJPA;
import com.my.model.CookieId;

@Service
public class CookieCache {

	// @Resource
	private JedisPool jedisPool = new JedisPool();

	@Resource
	private CookieIdJPA cookieIdJPA;
//
//	// @CachePut(key = "#key", value = "cookie")
//	public String add(String key, CookieId cookieId) {
//		CookieId cookie = cookieIdJPA.save(cookieId);
//		return cookie.getCookiesId();
//	}
//
//	// @Cacheable(value = "cookie", key = "#cookieId")
//	public String getCookie(String cookieId) throws Exception {
//		CookieId cookie = cookieIdJPA.findOne(cookieId);
//		if (null == cookie) {
//			return "false";
//		} else {
//			return "true";
//		}
//		// return cookie.getCookiesId();
//	}

	// @CachePut(key = "#key", value = "cookie")
	public Boolean jedisAdd(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		jedis.set(key, value);
		return true;
	}

	public String jedisGet(String key) {
		Jedis jedis = jedisPool.getResource();
		String value = jedis.get(key);
		if (value == null) {
			return "";
		}
		return value;
	}

	// @CacheEvict(value = "cookie", key = "#key")
	public void delete(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.del(key);
		return;

	}

	// 存储集合
	public void jedisSAdd(String globalSessionId, String localSessionIdStr) {
		Jedis jedis = jedisPool.getResource();
		jedis.sadd(globalSessionId, localSessionIdStr);
		return;
	}

	public void save(CookieId localCookieId) {
		cookieIdJPA.save(localCookieId);
	}

	public CookieId getCookieId(String cookieId) {
		return cookieIdJPA.jpaFindOne(cookieId);
	}

	public void jpaDelete(String globalSessionId) {
		cookieIdJPA.jpaDelete(globalSessionId);
	}
}
