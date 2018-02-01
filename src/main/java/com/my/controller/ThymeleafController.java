package com.my.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.cache.CookieCache;
import com.my.util.ToolsUtil;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

	@Resource
	private CookieCache cookieCache;

	@RequestMapping(value = "/app1")
	public String pageLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/app1";
	}

	@RequestMapping(value = "/app2")
	public String pageLogin2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/app2";
	}
	
	@RequestMapping(value = "/app3")
	public String authLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String logOutSessionId = ToolsUtil.getCookieValueByName(request, "logoutSessionId");

		// 直接将cookie中与用户有关的cookid记录全部删除
		String globalSessionId = ToolsUtil.getCookieValueByName(request, "globalSessionId");
		cookieCache.jpaDelete(globalSessionId);
		return "logout";
	}

}
