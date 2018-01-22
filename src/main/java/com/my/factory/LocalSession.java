package com.my.factory;

/**
 * 集体产品
 * 
 * @author by_ww
 *
 */
public class LocalSession implements AbstractSession {

	// public static Map<String, HttpSession> localSessionMap = new
	// HashMap<String, HttpSession>();

	private String sessionIdStr;

	private String userName;

	private String passWord;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setSessionIdStr(String sessionIdStr) {
		this.sessionIdStr = sessionIdStr;
	}

	public String getSessionIdStr() {
		return sessionIdStr;
	}

	public LocalSession() {
		super();
	}

	public LocalSession(String sessionIdStr, String userName, String passWord) {
		super();
		this.sessionIdStr = sessionIdStr;
		this.userName = userName;
		this.passWord = passWord;
	}

	@Override
	public String toString() {
		return "{\"sessionIdStr\":\"" + sessionIdStr + "\", \"userName\":\"" + userName + "\", \"passWord\":\""
				+ passWord + "\"} ";
	}

}
