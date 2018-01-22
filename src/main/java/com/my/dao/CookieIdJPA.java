package com.my.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.model.CookieId;

public interface CookieIdJPA extends JpaRepository<CookieId, String> {

}
