package com.my.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "token_info")
public class TokenInfo implements Serializable  {
	
	@Id
	// id自动生成
	@GeneratedValue
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "sso_client")
	private String ssoClient;

	@Column(name = "global_sessionid")
	private String globalSessionId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSsoClient() {
		return ssoClient;
	}

	public void setSsoClient(String ssoClient) {
		this.ssoClient = ssoClient;
	}

	public String getGlobalSessionId() {
		return globalSessionId;
	}

	public void setGlobalSessionId(String globalSessionId) {
		this.globalSessionId = globalSessionId;
	}

	@Override
	public String toString() {
		return "{\"userId\":\"" + userId + "\",\"userName\":\"" + userName
				+ "\",\"ssoClient\":\"" + ssoClient
				+ "\",\"globalSessionId\":\"" + globalSessionId + "\"} ";
	}
	
	

}
