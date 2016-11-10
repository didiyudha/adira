package com.adira.service.auditee;

import com.adira.dao.AuditTokenRepository;
import com.adira.entity.Audit;
import com.adira.entity.AuditToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by didiyudha on 10/11/16.
 */
@Service("auditeeService")
public class AuditeeServiceImpl implements AuditeeService {

    @Autowired
    private AuditTokenRepository auditTokenRepository;

    @Override
    public AuditToken findByIdAndToken(String id, String token) {
        AuditToken auditToken = auditTokenRepository.findByAuditAndToken(id, token);
        return auditToken;
    }
}
