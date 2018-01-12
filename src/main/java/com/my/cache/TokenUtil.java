package com.my.cache;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.my.dao.TokenInfoJPA;
import com.my.model.TokenInfo;

@Service
public class TokenUtil {

	@Resource
	private TokenInfoJPA tokenInfoJPA;

	// 存储临时令牌到redis中，存活期60秒
	@CachePut(key = "#tokenId", value = "default")
	public String setToken(String tokenId, TokenInfo tokenInfo) {
		TokenInfo token = tokenInfoJPA.save(tokenInfo);
		// 这边存入redis,设置存活期为60秒
		return token.toString();
	}

	// 根据token键取TokenInfo
	public TokenInfo getToken(String token) {

		// 根据token去redis中查询tokenInfo
		return null;
	}

	// 删除某个 token键值
	public void delToken(String tokenId) {
	}

}
