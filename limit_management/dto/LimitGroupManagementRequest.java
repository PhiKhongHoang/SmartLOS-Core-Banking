package com.ktn3.core_banking.limit_management.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LimitGroupManagementRequest {	
	private List<Long> orgIds;
	private BigDecimal groupLimitVnd;	
	private BigDecimal groupLimitUsd;	
}
