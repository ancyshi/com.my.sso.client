package com.my.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

	@RequestMapping(value = "/login")
	public String pageLogin() throws Exception {

		return "/login";

	}
}
