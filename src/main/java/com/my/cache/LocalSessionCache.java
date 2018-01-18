package com.my.cache;

import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LocalSessionCache {
	@Cacheable(value="localSessions",key="#sessionId")
    public String cacheable(String sessionId) throws Exception {
        return "false";
    }

    /**
     * 用于更新数据库或新增数据时的注解，更新redis服务器中该value的值
     */
    @CachePut(value="localSessions",key="#sessionId")
    public String cachePut(String sessionId, HttpSession session) throws Exception {
        return "false";
    }

    /**
     * 用于删除数据操作时的注解，删除redis数据库中该value对应的数据
     */
    @CacheEvict(value="localSessions",key="#key")
    public String cacheEvict(String key) throws Exception {
        return "false";
    }

}
