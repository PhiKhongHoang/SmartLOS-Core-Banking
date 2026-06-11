package com.ktn3.core_banking.limit_management.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ktn3.core_banking.org.constant.OrgType;
import com.ktn3.core_banking.org.entity.OrgUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "limit_management")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LimitManagement {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String groupName;
	
	private Long orgId;
	
	private BigDecimal limitVnd;
	
	private BigDecimal limitUsd;
	
	private BigDecimal groupLimitVnd;
	
	private BigDecimal groupLimitUsd;
	
	private Long createdBy;
	private LocalDateTime createdAt;
	private Long updatedBy;
	private LocalDateTime updatedAt;
	
}
