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

	private String applicationName;

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

	public LocalSession(String sessionIdStr, String userName, String passWord,String applicationName) {
		super();
		this.sessionIdStr = sessionIdStr;
		this.userName = userName;
		this.passWord = passWord;
		this.applicationName = applicationName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"sessionIdStr\":\"")
				.append(sessionIdStr).append('\"');
		sb.append(",\"applicationName\":\"")
				.append(applicationName).append('\"');
		sb.append(",\"userName\":\"")
				.append(userName).append('\"');
		sb.append(",\"passWord\":\"")
				.append(passWord).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
