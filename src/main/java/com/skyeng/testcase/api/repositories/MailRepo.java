package com.skyeng.testcase.api.repositories;

import com.skyeng.testcase.model.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepo extends JpaRepository<MailEntity, Long> {
}
