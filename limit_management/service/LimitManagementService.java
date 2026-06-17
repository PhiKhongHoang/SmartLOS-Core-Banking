package com.ktn3.core_banking.limit_management.service;

import org.springframework.data.domain.Pageable;

import com.ktn3.core_banking.common.response.PageResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementRequest;
import com.ktn3.core_banking.limit_management.dto.LimitManagementResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementSearchRequest;

public interface LimitManagementService {
	LimitManagementResponse update(LimitManagementRequest request);
	
	LimitManagementResponse findById(Long id);
	
	PageResponse<LimitManagementResponse> search(
			LimitManagementSearchRequest request,
		    Pageable pageable);
}
