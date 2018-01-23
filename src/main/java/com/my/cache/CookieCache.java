package com.my.cache;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.my.dao.CookieIdJPA;
import com.my.model.CookieId;

@Service
public class CookieCache {

	@Resource
	private CookieIdJPA cookieIdJPA;

	// @Resource
	private JedisPool jedisPool = new JedisPool();

//	@CachePut(key = "#key", value = "cookie")
	public String add(String key, CookieId cookieId) {
		CookieId cookie = cookieIdJPA.save(cookieId);
		return cookie.getCookiesId();
	}

//	@Cacheable(value = "cookie", key = "#cookieId")
	public String getCookie(String cookieId) throws Exception {
		CookieId cookie = cookieIdJPA.findOne(cookieId);
		if (null == cookie) {
			return "true";
		} else {
			return "false";
		}
//		return cookie.getCookiesId();
	}

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

//	@CacheEvict(value = "cookie", key = "#key")
	public void delete(String key) {
		cookieIdJPA.deleteAll();
		return;

	}

}
