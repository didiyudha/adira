package com.adira.job;

import com.adira.dao.AuditDao;
import com.adira.service.AuditService;

/**
 * Created by didiyudha on 21/10/16.
 */
public class AuditJob implements Runnable {
    private AuditDao auditDao;
    private AuditService auditService;

    public AuditJob(AuditDao auditDao, AuditService auditService) {
        this.auditDao = auditDao;
        this.auditService = auditService;
    }

    @Override
    public void run() {
        /**
         * Do send email here
         */
    }
}
