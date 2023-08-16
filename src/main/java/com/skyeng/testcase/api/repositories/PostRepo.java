package com.skyeng.testcase.api.repositories;

import com.skyeng.testcase.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<PostEntity, Long> {
}
