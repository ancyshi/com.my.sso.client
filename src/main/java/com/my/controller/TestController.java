package com.my.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.my.cache.CookieCache;
import com.my.model.CookieId;

@RestController
@RequestMapping(value = "/test")
public class TestController {

	@Resource
	private CookieCache cookieCache;

	@RequestMapping(value = "/redis")
	public Object redis(@RequestBody JSONObject record) throws Exception {

		// 产生临时的token
		CookieId localCookieId = new CookieId();
		localCookieId.setCookiesId("afefefe");
		cookieCache.jedisSAdd("fe", localCookieId.getCookiesId());

		// cookieCache.delete("fe");

		return localCookieId.getCookiesId();

	}
}
