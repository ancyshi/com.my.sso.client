package com.my.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.my.cache.CookieCache;
import com.my.cache.StudentCache;
import com.my.cache.TokenUtil;
import com.my.util.ToolsUtil;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

	@Autowired
	private Environment env;

	@Resource
	private StudentCache studentCache;

	@Resource
	private TokenUtil tokenUtil;

	@Resource
	private CookieCache cookieCache;

	@RequestMapping(value = "/app1")
	public String pageLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String app1SessionId = ToolsUtil.getCookieValueByName(request, "app1SessionId");

		// 如果localSeeionId不存在，或者已经退出了，就要去登录
		if (app1SessionId == null || !cookieCache.getCookie(app1SessionId).equals("true")) {
			// 重定向到认证中心
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("returnURL", "app1");
			String redirectURL = ToolsUtil.addressAppend("localhost", "8077", "/server/page/login", map);
			response.sendRedirect(redirectURL);
			return null;
		}

		return "/app1";

	}

	@RequestMapping(value = "/app2")
	public String pageLogin2(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String app2SessionId = ToolsUtil.getCookieValueByName(request, "app2SessionId");

		// 如果localSeeionId不存在，就重定向到SSOServer的接口/sso/page/login
		if (app2SessionId == null || cookieCache.getCookie(app2SessionId).equals("false")) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("returnURL", "app2");
			String redirectURL = ToolsUtil.addressAppend("localhost", "8077", "/server/page/login", map);
			response.sendRedirect(redirectURL);
			return null;

		}

		return "/app2";

	}
	
	@RequestMapping(value = "/logout")
	public String authLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String logOutSessionId = ToolsUtil.getCookieValueByName(request, "logoutSessionId");

		// 如果localSeeionId不存在，就重定向到SSOServer的接口/sso/page/login
		if (logOutSessionId == null || cookieCache.getCookie(logOutSessionId).equals("false")) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("returnURL", "logout");
			String redirectURL = ToolsUtil.addressAppend("localhost", "8077", "/server/page/login", map);
			response.sendRedirect(redirectURL);
			return null;

		}
		// 直接将cookie中与用户有关的cookid记录全部删除
		cookieCache.delete("");
		return "logout";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String user = (String) request.getParameter("user");
		String passWord = (String) request.getParameter("password");

		return "user:" + user;

	}

	@RequestMapping(value = "/login1")
	public String login1(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		model.addAttribute("returnURL", "app1");
		return "/login";

	}

	String token = UUID.randomUUID().toString();

	@RequestMapping(value = "/redis")
	public void redis(@RequestBody JSONObject record) throws Exception {

		// 产生临时的token
		String aString = "aa";
		String valString = "feafe";
		cookieCache.jedisAdd(aString, valString);
		String aValue = cookieCache.jedisGet(aString);
		return;

	}
}
