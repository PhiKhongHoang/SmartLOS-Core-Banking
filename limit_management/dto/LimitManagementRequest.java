package com.ktn3.core_banking.limit_management.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LimitManagementRequest {
	private String groupName;
	
	private Long orgId;
	
	private BigDecimal limitVnd;
	
	private BigDecimal limitUsd;
	
	private BigDecimal groupLimitVnd;
	
	private BigDecimal groupLimitUsd;	
}
