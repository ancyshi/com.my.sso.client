package com.my.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.util.ToolsUtil;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

	@Autowired
	private Environment env;

	@RequestMapping(value = "/app1")
	public String pageLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// HttpSession session = request.getSession();

		String app1SessionId = ToolsUtil.getCookieValueByName(request, "app1SessionId");

		String returnURL = (String) request.getAttribute("returnURL");

		// 如果localSeeionId不存在，就重定向到SSOServer的接口/sso/page/login
		if (app1SessionId == null) {
			// 重定向到认证中心
			response.sendRedirect("http://localhost:8077/server/page/login?returnURL=app1");
			// // 去server验证是否登陆,没有登录的话，server会给浏览器一个登录界面，如果登录了，会返回一个token
			// JSONObject resultObj = redirectToServer(request,
			// "localhost:8077", "/sso/page/login");
			//
			// if (resultObj.get("tokenInfo") == null) {
			// return resultObj.getString("returnURL");
			// }
			// // 拿着token去验证是否正确，调用server的接口是：/sso/auth/verify
			// JSONObject tokenVerify = redirectToServer(request,
			// "localhost:8077", "/sso/auth/verify");
			//
			// if (!tokenVerify.getBooleanValue("tokenVerify")) {
			// return tokenVerify.getString("returnURL");
			// }
			//
			// // 验证成功之后生成局部httpSession
			// session = request.getSession(true);
			// String localSessionId = session.getId();
			// LocalSessions.addSession(localSessionId, session);
			//
			// // 采用cookie的方式记录下两个sessionId
			// Cookie localSessionCookie = new Cookie("app1SessionId",
			// "cookievalue");
			// localSessionCookie.setPath("/");
			// localSessionCookie.setSecure(true);
			// Cookie globalSessionCookie = new Cookie("globalSessionId",
			// "cookievalue");
			// globalSessionCookie.setPath("/");
			// globalSessionCookie.setSecure(true);
			// response.addCookie(globalSessionCookie);
			// response.addCookie(localSessionCookie);
		}

		return returnURL;

	}

	// "/sso/page/login"
	private JSONObject redirectToServer(HttpServletRequest request, String server, String address)
			throws ClientProtocolException, IOException {

		JSONObject resultObj = new JSONObject();
		String verifyURL = "http://" + server + address;

		HttpClient httpClient = HttpClients.custom().build();

		HttpGet httpGet = new HttpGet(verifyURL);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.OK.value()) {

				String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");

				resultObj = JSON.parseObject(result);
				return resultObj;

			}
		} catch (Exception e) {
			// 返回登录页面
			String loginURL = "/login";
			resultObj.put("returnURL", loginURL);
			return resultObj;
		}
		return resultObj;

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String user = (String) request.getParameter("user");
		String passWord = (String) request.getParameter("password");

		return "user:" + user;

	}

	@RequestMapping(value = "/login1")
	public String login1(HttpServletRequest request, HttpServletResponse response) throws Exception {

		return "/login";

	}
}
