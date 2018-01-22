package com.my.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.*;

public class LocalSessions {
	// 存放所有局部会话
	public static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

	public static void addSession(String sessionId, HttpSession session) {
		sessions.put(sessionId, session);
	}

	public static void delSession(String sessionId) {
		sessions.remove(sessionId);
	}

	// 根据id得到session
	public static HttpSession getSession(String sessionId) {
		return sessions.get(sessionId);
	}
	
	
}
