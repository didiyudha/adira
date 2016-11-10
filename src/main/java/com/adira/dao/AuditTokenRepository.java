package com.adira.dao;

import com.adira.entity.AuditToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by didiyudha on 10/11/16.
 */
public interface AuditTokenRepository extends JpaRepository<AuditToken, String> {
    @Query("SELECT auditToken FROM AuditToken auditToken WHERE auditToken.audit.id = ?1 and auditToken.token = ?2 ")
    AuditToken findByAuditAndToken(String auditId, String token);
}
