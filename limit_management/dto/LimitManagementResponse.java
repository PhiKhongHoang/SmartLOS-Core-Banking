package com.ktn3.core_banking.limit_management.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class LimitManagementResponse {
	private Long id;
	
	private String groupName;
	
	private Long orgId;
	
	private BigDecimal limitVnd;
	private String limitVndStr;
	
	private BigDecimal limitUsd;
	private String limitUsdStr;
	
	private BigDecimal groupLimitVnd;
	private String groupLimitVndStr;
	
	private BigDecimal groupLimitUsd;
	private String groupLimitUsdStr;
	
	private Long createdBy;
	private LocalDateTime createdAt;
	private Long updatedBy;
	private LocalDateTime updatedAt;
}
