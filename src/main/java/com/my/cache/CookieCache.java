package com.my.cache;

import javax.servlet.http.Cookie;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.my.model.Student;

@Service
public class CookieCache {
	@CachePut(key = "#key", value = "cookie")
	public String add(String key, Cookie cookie) {
		return "save success";
	}
	
	@Cacheable(value="cookie",key="#cookieId")
    public String getCookie(String cookieId) throws Exception {
		return null;
    }

}
