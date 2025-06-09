package com.cknb.htPlatform.status.repository;

import com.cknb.htPlatform.entity.UserMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTotalRepository extends JpaRepository<UserMasterEntity, Long> {
    Long countByUserRole(String userRole);
}
