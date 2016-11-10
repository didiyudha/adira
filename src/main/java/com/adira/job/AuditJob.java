package com.adira.job;

import com.adira.dao.AuditRepository;
import com.adira.entity.Audit;
import com.adira.service.audit.AuditService;

import java.util.List;

/**
 * Created by didiyudha on 21/10/16.
 */
public class AuditJob implements Runnable {
    private AuditRepository auditRepository;
    private AuditService auditService;

    public AuditJob(AuditRepository auditRepository, AuditService auditService) {
        this.auditRepository = auditRepository;
        this.auditService = auditService;
    }

    @Override
    public void run() {
        /**
         * Do send email here
         */
        List<Audit> audits = (List<Audit>) auditRepository.findAll();
    }
}
