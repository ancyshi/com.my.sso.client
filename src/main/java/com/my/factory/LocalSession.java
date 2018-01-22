package com.my.factory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 集体产品
 * @author by_ww
 *
 */
public class LocalSession implements AbstractSession {
	
	public static Map<String, HttpSession> localSessionMap = new HashMap<String, HttpSession>();

	public String sessionIdStr;

	public HttpSession httpSession;

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


	
	public LocalSession() {
		super();
	}

	public LocalSession(String sessionIdStr, HttpSession httpSession) {
		super();
		this.sessionIdStr = sessionIdStr;
		this.httpSession = httpSession;
	}

	@Override
	public LocalSession generateSession(String sessionId, HttpSession session) {
		return new LocalSession(sessionId,session);
	}

}
