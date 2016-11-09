package com.adira.service.audit;

import com.adira.dao.AuditDao;
import com.adira.entity.Audit;
import com.adira.function.FunctionDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by didi-realtime on 27/09/16.
 */
@Service("auditService")
public class AuditServiceImpl implements AuditService {

    private static final String prefix = "AUDIT";
    private static final String separator = "/";

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

    @Override
    public String generateAuditName(Audit audit) {

        int year = FunctionDate.getYearFromDate(audit.getInitialDueDate());
        int month = FunctionDate.getMonthFromDate(audit.getInitialDueDate());
        int d = FunctionDate.getDateIndexFromDate(audit.getInitialDueDate());

        StringBuilder sb = new StringBuilder();
        sb
                .append(prefix)
                .append(separator)
                .append(year)
                .append(separator)
                .append(month)
                .append(separator)
                .append(d)
                .append(separator);

        return sb.toString();
    }
}
