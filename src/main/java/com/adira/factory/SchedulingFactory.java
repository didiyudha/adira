package com.adira.factory;

import com.adira.dao.AuditDao;
import com.adira.job.AuditJob;
import com.adira.service.audit.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by didiyudha on 21/10/16.
 */
@Component
public class SchedulingFactory {

    private final String cronExpression = "0 1 12 1/1 * ?";

    @Autowired
    private AuditDao auditDao;
    @Autowired
    private AuditService auditService;
    @Autowired
    private TaskScheduler taskScheduler;

    @PostConstruct
    public void init() {
        taskScheduler.schedule(new AuditJob(auditDao, auditService), new CronTrigger(cronExpression));
    }
}
