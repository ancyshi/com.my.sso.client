package com.my.factory;

import javax.servlet.http.HttpSession;

public class MySession {

	private String sessionIdStr;

	private HttpSession httpSession;

	public String getSessionIdStr() {
		return sessionIdStr;
	}

	public void setSessionIdStr(String sessionIdStr) {
		this.sessionIdStr = sessionIdStr;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

}
