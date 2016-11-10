package com.adira.service.auditee;

import com.adira.entity.Audit;
import com.adira.entity.AuditToken;

/**
 * Created by didiyudha on 10/11/16.
 */
public interface AuditeeService {
    AuditToken findByIdAndToken(String id, String token);
}
