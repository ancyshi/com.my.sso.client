package com.my.factory;

import javax.servlet.http.HttpSession;

/**
 * 集体产品
 * @author by_ww
 *
 */
public class LocalSession extends MySession implements AbstractSession {

	
	
	@Override
	public LocalSession generateSession(String sessionId, HttpSession session) {
		// TODO Auto-generated method stub
		return null;
	}

}
