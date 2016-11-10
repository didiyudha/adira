package com.adira.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by didiyudha on 10/11/16.
 */
@Entity
@Table(name = "audit_tokens")
public class AuditToken {

    public enum  AuditTokenStatus {
        ACTIVE,
        INACTIVE
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne
    @JoinColumn(name = "audit_id", nullable = false, unique = true)
    private Audit audit;

    @Column(name = "token", nullable = false, unique = true)
    @Type(type = "text")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(10) default 'INACTIVE' ")
    private AuditTokenStatus status;

    public AuditToken() {
    }

    public AuditToken(Audit audit, String token, AuditTokenStatus status) {
        this.audit = audit;
        this.token = token;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuditTokenStatus getStatus() {
        return status;
    }

    public void setStatus(AuditTokenStatus status) {
        this.status = status;
    }
}
