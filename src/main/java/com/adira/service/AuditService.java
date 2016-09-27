package com.adira.service;

import com.adira.entity.Audit;

import java.util.List;

/**
 * Created by didi-realtime on 27/09/16.
 */
public interface AuditService {
    List<Audit> findAll();

    Audit findById(String id);
}
