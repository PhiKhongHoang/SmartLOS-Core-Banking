package com.ktn3.core_banking.limit_management.service;

import org.springframework.data.domain.Pageable;

import com.ktn3.core_banking.common.response.PageResponse;
import com.ktn3.core_banking.limit_management.dto.LimitGroupManagementRequest;
import com.ktn3.core_banking.limit_management.dto.LimitGroupManagementResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementSearchRequest;

public interface LimitGroupManagementService {
	LimitGroupManagementResponse update(String groupName, LimitGroupManagementRequest request);
	
	LimitGroupManagementResponse findByGroupName(String groupName);
	
	PageResponse<LimitGroupManagementResponse> search(
			LimitManagementSearchRequest request,
		    Pageable pageable);
}
