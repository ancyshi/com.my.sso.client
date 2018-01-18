package com.my.factory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public interface AbstractSession {
	// 存放所有局部会话
	public Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

	public void addSession(String sessionId, HttpSession session);

	public void delSession(String sessionId);

	// 根据id得到session
	public HttpSession getSession(String sessionId);

}
