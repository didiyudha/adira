package com.adira.dao;

import com.adira.entity.Audit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by didi-realtime on 27/09/16.
 */
@Repository
public interface AuditRepository extends PagingAndSortingRepository<Audit, String> {
    List<Audit> findByDeletedFalse();

    Audit findByIdAndDeletedFalse(String id);

    @Query("SELECT audit FROM Audit audit WHERE audit.deleted = FALSE AND UPPER(audit.referenceNo) LIKE UPPER(?1) ")
    List<Audit> findByReferenceNo(String refNo);
}
