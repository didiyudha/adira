package com.adira.entity;

import com.adira.enumeration.RiskLevel;
import com.adira.enumeration.Status;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by didi-realtime on 27/09/16.
 */
@Entity
@Table(name = "audits")
public class Audit {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "audit_year")
    private Integer auditYear;

    @Column(name = "auditor")
    private String auditor;

    @Column(name = "domain")
    private String domain;

    @Column(name = "unit")
    private String unit;

    @Column(name = "pic")
    private String pic;

    @Column(name = "audit_issue")
    private String auditIssue;

    @Column(name = "audit_issue_description")
    @Type(type = "text")
    private String auditIssueDescription;

    @Column(name = "action_plan")
    @Type(type = "text")
    private String actionPlan;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", columnDefinition = "varchar(10) default 'LOW'")
    private RiskLevel riskLevel;

    @Column(name = "outstanding_action_plan")
    @Type(type = "text")
    private String outstandingActionPlan;

    @Temporal(TemporalType.DATE)
    @Column(name = "initial_due_date")
    private Date initialDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Temporal(TemporalType.DATE)
    @Column(name = "first_rescheduled")
    private Date firstRescheduled;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "second_rescheduled")
    private Date secondRescheduled;

    @Column(name = "deleted", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private boolean deleted = false;

    @OneToMany
    @JoinTable(
            name = "audit_comments",
            joinColumns = @JoinColumn(name = "audit_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "reference_no", nullable = false)
    private String referenceNo;

    public Audit() {
    }

    public Audit(int auditYear, String auditor, String domain, String unit, String pic, String auditIssue,
                 String auditIssueDescription, String actionPlan, RiskLevel riskLevel, String outstandingActionPlan,
                 Date initialDueDate, Status status, boolean deleted) {

        this.auditYear = auditYear;
        this.auditor = auditor;
        this.domain = domain;
        this.unit = unit;
        this.pic = pic;
        this.auditIssue = auditIssue;
        this.auditIssueDescription = auditIssueDescription;
        this.actionPlan = actionPlan;
        this.riskLevel = riskLevel;
        this.outstandingActionPlan = outstandingActionPlan;
        this.initialDueDate = initialDueDate;
        this.status = status;
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAuditYear() {
        return auditYear;
    }

    public void setAuditYear(Integer auditYear) {
        this.auditYear = auditYear;
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

    public String getAuditIssue() {
        return auditIssue;
    }

    public void setAuditIssue(String auditIssue) {
        this.auditIssue = auditIssue;
    }

    public String getAuditIssueDescription() {
        return auditIssueDescription;
    }

    public void setAuditIssueDescription(String auditIssueDescription) {
        this.auditIssueDescription = auditIssueDescription;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
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

    public Date getFirstRescheduled() {
        return firstRescheduled;
    }

    public void setFirstRescheduled(Date firstRescheduled) {
        this.firstRescheduled = firstRescheduled;
    }

    public Date getSecondRescheduled() {
        return secondRescheduled;
    }

    public void setSecondRescheduled(Date secondRescheduled) {
        this.secondRescheduled = secondRescheduled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }
}
