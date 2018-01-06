package com.my.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

	@Autowired
	private Environment env;

	@RequestMapping(value = "/app1")
	public String pageLogin(HttpServletRequest request) throws Exception {

		// 如果localSeeionId不存在，就重定向到SSOServer的接口/sso/page/login
		if (request.getSession().getId() == null) {
			redirectToServer(request, "localhost:8077", "/sso/page/login");

			// 生成局部
		}

		return "/app1";

	}

	private void redirectToServer(HttpServletRequest request, String server, String address)
			throws ClientProtocolException, IOException {
		String verifyURL = "http://" + server + address;

		// 把Globalsessionid传给server,returnURL传入用于重定向
		HttpClient httpClient = new DefaultHttpClient();
		// serverName作为本应用标识
		HttpGet httpGet = new HttpGet(verifyURL + "?globalSession=" + request.getSession().getId());
		HttpResponse httpResponse = httpClient.execute(httpGet);

	}

}
