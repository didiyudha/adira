package com.adira.dto;

/**
 * Created by didiyudha on 10/11/16.
 */
public class AuditDto {
    private String id;
    private String referenceNo;
    private String auditIssue;
    private String comment;
    private String pic;
    private String auditor;

    public AuditDto(String id, String referenceNo, String auditIssue, String comment, String pic, String auditor) {
        this.id = id;
        this.referenceNo = referenceNo;
        this.auditIssue = auditIssue;
        this.comment = comment;
        this.pic = pic;
        this.auditor = auditor;
    }

    public AuditDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getAuditIssue() {
        return auditIssue;
    }

    public void setAuditIssue(String auditIssue) {
        this.auditIssue = auditIssue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }
}
