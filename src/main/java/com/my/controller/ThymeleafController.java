package com.my.controller;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.cache.StudentCache;
import com.my.model.Student;
import com.my.util.ToolsUtil;

@RestController
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

	@Autowired
	private Environment env;

	@Resource
	private StudentCache studentCache;

	@RequestMapping(value = "/app1")
	public String pageLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {


		String app1SessionId = ToolsUtil.getCookieValueByName(request, "app1SessionId");


		// 如果localSeeionId不存在，就重定向到SSOServer的接口/sso/page/login
		if (app1SessionId == null) {
			// 重定向到认证中心
			response.sendRedirect("http://localhost:8077/server/page/login?returnURL=app1");
			
		}

		return "/app1";

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
	public Object redis(@RequestBody JSONObject record) throws Exception {
		Student student = JSONObject.toJavaObject(record, Student.class);
		studentCache.add(token, student);
		return student;

	}
}
