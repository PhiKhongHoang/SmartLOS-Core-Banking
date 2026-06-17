package com.ktn3.core_banking.limit_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ktn3.core_banking.limit_management.entity.LimitManagement;

@Repository
public interface LimitManagementRepository extends JpaRepository<LimitManagement, Long>,
					JpaSpecificationExecutor<LimitManagement> {
	void deleteAllByGroupName(String groupName);
}
