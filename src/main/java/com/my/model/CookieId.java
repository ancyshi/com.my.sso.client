package com.my.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cookies")
public class CookieId implements Serializable {
	@Id
	@Column(name = "cookie_id")
	private String cookiesId;

	@Column(name="global_id")
	private  String globalId;

	public String getCookiesId() {
		return cookiesId;
	}

	public void setCookiesId(String cookiesId) {
		this.cookiesId = cookiesId;
	}

	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"cookiesId\":\"")
				.append(cookiesId).append('\"');
		sb.append(",\"globalId\":\"")
				.append(globalId).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
