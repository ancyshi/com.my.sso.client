package com.my.dao;

import com.my.model.CookieId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CookieIdJPA extends JpaRepository<CookieId, String> {

    @Modifying
    @Transactional
    @Query(value = "delete from cookies where cookie_id = ?1 or global_id=?1", nativeQuery = true)
    int jpaDelete(String cookieId);

}
