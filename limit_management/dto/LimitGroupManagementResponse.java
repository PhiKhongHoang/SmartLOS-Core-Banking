package com.ktn3.core_banking.limit_management.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ktn3.core_banking.org.dto.OrgIdNameResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LimitGroupManagementResponse {

	private String groupName;
	private List<OrgIdNameResponse> orgResponses;
	
	private BigDecimal groupLimitVnd;
	private String groupLimitVndStr;
	
	private BigDecimal groupLimitUsd;
	private String groupLimitUsdStr;
	
	private Long createdBy;
	private LocalDateTime createdAt;
	private Long updatedBy;
	private LocalDateTime updatedAt;
}
