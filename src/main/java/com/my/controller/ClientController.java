package com.my.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.my.cache.CookieCache;
import com.my.cache.LocalSessionCache;
import com.my.factory.AbstractFactory;
import com.my.factory.LocalSession;
import com.my.factory.SessionFactory;
import com.my.model.CookieId;
import com.my.util.MyHttpUtils;
import com.my.util.ToolsUtil;

@Controller
@RequestMapping(value = "/client")
public class ClientController {

	@Autowired
	private Environment env;

	@Resource
	private CookieCache cookieCache;

	@Resource
	private LocalSessionCache localSessionCache;

	private AbstractFactory abstractFactory = new SessionFactory();

	private MyHttpUtils myHttpUtils = new MyHttpUtils();

	@RequestMapping(value = "/auth/check")
	public String authCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 接受来自认证中心的token
		String token = request.getParameter("token");
		if (token == null) {
			return "/login";
		}

		// 去server端验证token的真伪
		String tokenInfoStr = "";
		try {
			tokenInfoStr = checkToken(token, request);
		} catch (Exception e) {
			return "/login";
		}
		if (StringUtils.isEmpty(tokenInfoStr)) {
			return "/login";
		}

		JSONObject tokenInfo = JSONObject.parseObject(tokenInfoStr);
		// 如有效简历本地会话
		HttpSession session = request.getSession(true);
		String localSessionId = session.getId();

		// todo
		LocalSession localSession = (LocalSession) abstractFactory.generateSession();
		localSession.setSessionIdStr(localSessionId);
		localSession.setUserName(tokenInfo.getString("userName"));
		localSession.setPassWord(tokenInfo.getString("passWord"));
		try {
			localSessionCache.cachePut(localSessionId, localSession);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 采用cookie的方式记录下两个sessionId
		Cookie localSessionCookie = new Cookie(request.getParameter("returnURL") + "SessionId", localSessionId);
		localSessionCookie.setPath("/");
		localSessionCookie.setMaxAge(-1);
		Cookie globalSessionCookie = new Cookie("globalSessionId", tokenInfo.getString("globalSessionId"));
		globalSessionCookie.setPath("/");
		globalSessionCookie.setMaxAge(-1);
		response.addCookie(globalSessionCookie);
		response.addCookie(localSessionCookie);

		CookieId localCookieId = new CookieId();
		localCookieId.setCookiesId(localSession.getSessionIdStr());
		cookieCache.add(request.getParameter("returnURL") + "SessionId", localCookieId);

		CookieId globalCookieId = new CookieId();
		globalCookieId.setCookiesId(tokenInfo.getString("globalSessionId"));
		cookieCache.add("globalSessionId", globalCookieId);

		// 验证token之后，重定向到请求的页面
		String redirectURL = ToolsUtil.addressAppend("localhost", "8078",
				"/thymeleaf/" + request.getParameter("returnURL"), null);
		response.sendRedirect(redirectURL);
		return null;
	}

	@RequestMapping(value = "/auth/logout")
	public Long authLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String logOutSessionId = ToolsUtil.getCookieValueByName(request, "logOutSessionId");

		// 如果localSeeionId不存在，就重定向到SSOServer的接口/sso/page/login
		if (logOutSessionId == null || cookieCache.getCookie(logOutSessionId).equals("false")) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("returnURL", "auth/logout");
			String redirectURL = ToolsUtil.addressAppend("localhost", "8077", "/server/page/login", map);
			response.sendRedirect(redirectURL);
			return null;

		}
		// 直接将cookie中与用户有关的cookid记录全部删除
		cookieCache.delete("");
		return null;
	}

	// 这个方法有出入
	private String checkToken(String token, HttpServletRequest request) throws ClientProtocolException, IOException {
		// 向认证中心发送验证token请求
		// 去server端的接口/server/auth/verify验证token的有效性
		String verifyURL = "http://" + env.getProperty("sso.server") + env.getProperty("sso.server.verify");
		// serverName作为本应用标识
		String tokenInfo = "";
		MyHttpUtils myHttpUtils = new MyHttpUtils();
		JSONObject reqObj = new JSONObject();
		reqObj.put("token", token);
		tokenInfo = myHttpUtils.httpPostJsonObj(verifyURL, reqObj, "utf-8");

		return tokenInfo;

	}
}
