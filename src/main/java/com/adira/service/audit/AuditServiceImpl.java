package com.adira.service.audit;

import com.adira.dao.AuditDao;
import com.adira.entity.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by didi-realtime on 27/09/16.
 */
@Service("auditService")
public class AuditServiceImpl implements AuditService {
    @Autowired
    AuditDao auditDao;

    @Override
    public List<Audit> findAll() {
        return auditDao.findByDeletedFalse();
    }

    @Override
    public Audit findById(String id) {
        if (id == null || id.equals(""))
            return null;
        return auditDao.findByIdAndDeletedFalse(id);
    }
}
