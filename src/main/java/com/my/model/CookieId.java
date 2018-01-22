package com.my.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cookies")
public class CookieId implements Serializable {
	@Id
	@Column(name = "cookie_id")
	private String cookiesId;

	public String getCookiesId() {
		return cookiesId;
	}

	public void setCookiesId(String cookiesId) {
		this.cookiesId = cookiesId;
	}

}
