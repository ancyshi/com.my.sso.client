package com.my.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.my.util.LocalSessions;

@Controller
@RequestMapping(value = "/client")
public class ClientController {
	@Autowired
	private Environment env;

	@RequestMapping(value = "/auth/check")
	public String authCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 接受来自认证中心的token
		String token = request.getParameter("token");
		if (token == null) {
			return "/login";
		}

		// 去server端验证token的真伪
		checkToken(token, request);

		// 如有效简历本地会话
		HttpSession session = request.getSession(true);
		String localSessionId = session.getId();
		LocalSessions.addSession(localSessionId, session);

		// 采用cookie的方式记录下两个sessionId
		Cookie localSessionCookie = new Cookie("app1SessionId", "cookievalue");
		localSessionCookie.setPath("/");
		localSessionCookie.setSecure(true);
		Cookie globalSessionCookie = new Cookie("globalSessionId", "cookievalue");
		globalSessionCookie.setPath("/");
		globalSessionCookie.setSecure(true);
		response.addCookie(globalSessionCookie);
		response.addCookie(localSessionCookie);

		response.sendRedirect("http://localhost:8077" + request.getAttribute("returnURL"));

		return (String) request.getAttribute("returnURL");
	}

	@RequestMapping(value = "/auth/logout")
	public Long authLogout(JSONObject request) throws Exception {

		return null;
	}

	// 这个方法有出入
	private String checkToken(String token, HttpServletRequest request) {
		// 向认证中心发送验证token请求
		// String verifyURL = "http://" + server +
		// PropertiesConfigUtil.getProperty("sso.server.verify");

		// 去server端的接口/server/auth/verify验证token的有效性
		String verifyURL = "http://" + env.getProperty("sso.server") + env.getProperty("sso.server.verify");
		HttpClient httpClient = HttpClients.custom().build();
		// serverName作为本应用标识
		HttpGet httpGet = new HttpGet(verifyURL + "?token=" + token + "&localId=" + request.getSession().getId());
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.OK.value()) {
				String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");

				return "redirect:http://" + result;
			}
		} catch (Exception e) {
			// 返回登录页面
			String loginURL = "/login";
			return "redirect:" + loginURL;
		}

		return null;

	}
}
