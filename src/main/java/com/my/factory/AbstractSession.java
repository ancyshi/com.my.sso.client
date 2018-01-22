package com.my.factory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 抽象产品
 * @author by_ww
 *
 */
public interface AbstractSession {
	
	// 生成session
	public LocalSession generateSession(String sessionId, HttpSession session);

}
