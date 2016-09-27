package com.adira.entity;

import com.adira.enumeration.LevelResiko;
import com.adira.enumeration.Status;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by didi-realtime on 27/09/16.
 */
@Entity
@Table(name = "entities")
public class Audit {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tahun_audit")
    private Date tahunAudit;

    @Column(name = "auditor")
    private String auditor;

    @Column(name = "domain")
    private String domain;

    @Column(name = "unit")
    private String unit;

    @Column(name = "pic")
    private String pic;

    @Column(name = "isu_audit")
    private String isuAudit;

    @Column(name = "deskripsi_isu_audit")
    private String deskripsiIsuAudit;

    @Column(name = "action_plan")
    private String actionPlan;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_resion", columnDefinition = "varchar(10) default 'LOW'")
    private LevelResiko levelResiko;

    @Column(name = "outstanding_action_plan")
    private String outstandingActionPlan;

    @Temporal(TemporalType.DATE)
    @Column(name = "initial_due_date")
    private Date initialDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    public Audit() {
    }

    public Audit(Date tahunAudit, String auditor, String domain, String unit, String pic, String isuAudit,
                 String deskripsiIsuAudit, String actionPlan, LevelResiko levelResiko, String outstandingActionPlan,
                 Date initialDueDate, Status status) {
        this.tahunAudit = tahunAudit;
        this.auditor = auditor;
        this.domain = domain;
        this.unit = unit;
        this.pic = pic;
        this.isuAudit = isuAudit;
        this.deskripsiIsuAudit = deskripsiIsuAudit;
        this.actionPlan = actionPlan;
        this.levelResiko = levelResiko;
        this.outstandingActionPlan = outstandingActionPlan;
        this.initialDueDate = initialDueDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTahunAudit() {
        return tahunAudit;
    }

    public void setTahunAudit(Date tahunAudit) {
        this.tahunAudit = tahunAudit;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIsuAudit() {
        return isuAudit;
    }

    public void setIsuAudit(String isuAudit) {
        this.isuAudit = isuAudit;
    }

    public String getDeskripsiIsuAudit() {
        return deskripsiIsuAudit;
    }

    public void setDeskripsiIsuAudit(String deskripsiIsuAudit) {
        this.deskripsiIsuAudit = deskripsiIsuAudit;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public LevelResiko getLevelResiko() {
        return levelResiko;
    }

    public void setLevelResiko(LevelResiko levelResiko) {
        this.levelResiko = levelResiko;
    }

    public String getOutstandingActionPlan() {
        return outstandingActionPlan;
    }

    public void setOutstandingActionPlan(String outstandingActionPlan) {
        this.outstandingActionPlan = outstandingActionPlan;
    }

    public Date getInitialDueDate() {
        return initialDueDate;
    }

    public void setInitialDueDate(Date initialDueDate) {
        this.initialDueDate = initialDueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
