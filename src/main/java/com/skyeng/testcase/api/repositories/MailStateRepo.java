package com.skyeng.testcase.api.repositories;

import com.skyeng.testcase.model.MailStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MailStateRepo extends JpaRepository<MailStateEntity, Long> {
    @Query(value = "select * from mail_states where mail_id = :mailId order by created_at desc limit 1", nativeQuery = true)
    MailStateEntity findLastByMailId(@Param("mailId") Long mailId);
}
