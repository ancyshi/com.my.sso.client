package com.my.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.model.Student;
import com.my.model.TokenInfo;

public interface TokenInfoJPA extends JpaRepository<TokenInfo, Long>{

}
