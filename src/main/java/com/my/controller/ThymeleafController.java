package com.my.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.util.LocalSessions;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

	@Autowired
	private Environment env;

	@RequestMapping(value = "/app1")
	public String pageLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// 如果localSeeionId不存在，就重定向到SSOServer的接口/sso/page/login
		if (session == null || session.getId() == null) {

			// 去server验证是否登陆,没有登录的话，server会给浏览器一个登录界面，如果登录了，会返回一个token
			redirectToServer(request, "localhost:8077", "/sso/page/login");

			// 拿着token去验证是否正确，调用server的接口是：/sso/auth/verify

			// 验证成功之后生成局部httpSession
			session = request.getSession(true);
			String localSessionId = session.getId();
			LocalSessions.addSession(localSessionId, session);

			// 采用cookie的方式记录下两个sessionId
			Cookie localSessionCookie = new Cookie("localSessionId", "cookievalue");
			Cookie globalSessionCookie = new Cookie("globalSessionId", "cookievalue");
			response.addCookie(globalSessionCookie);
			response.addCookie(localSessionCookie);
		}

		return "/app1";

	}

	// "/sso/page/login"
	private void redirectToServer(HttpServletRequest request, String server, String address)
			throws ClientProtocolException, IOException {
		String verifyURL = "http://" + server + address;

		// 把Globalsessionid传给server,returnURL传入用于重定向
		HttpClient httpClient = new DefaultHttpClient();
		// serverName作为本应用标识
		HttpGet httpGet = new HttpGet(verifyURL + "?glocalSession=" + request.getSession());
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.OK.value()) {
				String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
				// 解析json数据
				ObjectMapper objectMapper = new ObjectMapper();
				VerifyBean verifyResult = objectMapper.readValue(result, VerifyBean.class);
				// 验证通过,应用返回浏览器需要验证的页面
				if (verifyResult.getRet().equals("0")) {
					Auth auth = new Auth();
					auth.setUserId(verifyResult.getUserId());
					auth.setUsername(verifyResult.getUsername());
					auth.setGlobalId(verifyResult.getGlobalId());
					request.getSession().setAttribute("auth", auth);
					// 建立本地会话，返回到请求页面
					String returnURL = request.getParameter("returnURL");
					return "redirect:http://" + returnURL;
				}
			}
		} catch (Exception e) {
			// 返回登录页面
			String loginURL = "/login";
			return "redirect:" + loginURL;
		}

	}

}
