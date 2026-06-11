package com.ktn3.core_banking.limit_management.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ktn3.core_banking.common.response.PageResponse;
import com.ktn3.core_banking.common.util.MoneyUtils;
import com.ktn3.core_banking.limit_management.dto.LimitManagementRequest;
import com.ktn3.core_banking.limit_management.dto.LimitManagementResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementSearchRequest;
import com.ktn3.core_banking.limit_management.entity.LimitManagement;
import com.ktn3.core_banking.limit_management.repository.LimitManagementRepository;
import com.ktn3.core_banking.limit_management.service.LimitManagementService;
import com.ktn3.core_banking.security.service.CurrentUserService;
import com.ktn3.core_banking.user.dto.UserResponse;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LimitManagementServiceImpl implements LimitManagementService{
	
	private final LimitManagementRepository limitManagementRepository;
	private final CurrentUserService currentUserService;

	@Override
	public LimitManagementResponse update(LimitManagementRequest request) {
		UserResponse userResponse = currentUserService.getCurrentUser();
		
		LimitManagement limitManagement = 
				LimitManagement.builder()
				.limitVnd(request.getLimitVnd())
				.limitUsd(request.getLimitUsd())
				.updatedAt(LocalDateTime.now())
				.updatedBy(userResponse.getId())
				.build();
		limitManagement = limitManagementRepository.save(limitManagement);
		
		return LimitManagementResponse.builder()
				.id(limitManagement.getId())
				.groupName(limitManagement.getGroupName())
				.orgId(limitManagement.getOrgId())
				.limitVnd(limitManagement.getLimitVnd())
				.limitVndStr(MoneyUtils.formatMoney(limitManagement.getLimitVnd(), "VND"))
				.limitUsd(limitManagement.getLimitUsd())
				.limitUsdStr(MoneyUtils.formatMoney(limitManagement.getLimitUsd(), "USD"))
				.groupLimitVnd(limitManagement.getGroupLimitVnd())
				.groupLimitVndStr(MoneyUtils.formatMoney(limitManagement.getGroupLimitVnd(), "VND"))
				.groupLimitUsd(limitManagement.getGroupLimitUsd())
				.groupLimitUsdStr(MoneyUtils.formatMoney(limitManagement.getGroupLimitUsd(), "USD"))
				.createdAt(limitManagement.getCreatedAt())
				.updatedAt(limitManagement.getUpdatedAt())
				.createdBy(limitManagement.getCreatedBy())
				.updatedBy(limitManagement.getUpdatedBy())
				.build();
	}

	@Override
	public LimitManagementResponse findById(Long id) {
		LimitManagement limitManagement = limitManagementRepository.findById(id).orElse(null);
		
		LimitManagementResponse limitManagementResponse = new LimitManagementResponse();
		if(limitManagement != null) {
			limitManagementResponse = LimitManagementResponse.builder()
					.id(limitManagement.getId())
					.groupName(limitManagement.getGroupName())
					.orgId(limitManagement.getOrgId())
					.limitVnd(limitManagement.getLimitVnd())
					.limitVndStr(MoneyUtils.formatMoney(limitManagement.getLimitVnd(), "VND"))
					.limitUsd(limitManagement.getLimitUsd())
					.limitUsdStr(MoneyUtils.formatMoney(limitManagement.getLimitUsd(), "USD"))
					.groupLimitVnd(limitManagement.getGroupLimitVnd())
					.groupLimitVndStr(MoneyUtils.formatMoney(limitManagement.getGroupLimitVnd(), "VND"))
					.groupLimitUsd(limitManagement.getGroupLimitUsd())
					.groupLimitUsdStr(MoneyUtils.formatMoney(limitManagement.getGroupLimitUsd(), "USD"))
					.createdAt(limitManagement.getCreatedAt())
					.updatedAt(limitManagement.getUpdatedAt())
					.createdBy(limitManagement.getCreatedBy())
					.updatedBy(limitManagement.getUpdatedBy())
					.build();
		}
		
		return limitManagementResponse;
	}
	
	@Override
	public PageResponse<LimitManagementResponse>
					search(LimitManagementSearchRequest request, Pageable pageable) {

	    Specification<LimitManagement>
	            spec = (root, query, cb) -> {	            	
	        List<Predicate> predicates = new ArrayList<>();

	        if (request.getGroupName()!= null
	                &&
                	!request.getGroupName().isBlank()) {

	            predicates.add(
	                    cb.equal(
	                            root.get("groupName"),
	                            request.getGroupName()
	                    )
	            );
	        }

	        if (request.getOrgId() != null) {
	            predicates.add(
	                    cb.equal(
	                            root.get("orgId"),
	                            request.getOrgId()
	                    )
	            );
	        }

	        return cb.and(
	                predicates.toArray(
	                        new Predicate[0]));
	    };


	    Page<LimitManagement>
	            page = limitManagementRepository.findAll(spec, pageable);

	    return PageResponse.<LimitManagementResponse>
	                    builder()
	            .items(
	            		page
                            .stream()
                            .map(this::map)
                            .toList()
	            )

	            .page(page.getNumber())
	            .size(page.getSize())
	            .totalElements(page.getTotalElements())
	            .totalPages(page.getTotalPages())
	            .build();
	}
	
	private
    LimitManagementResponse
    map(
            LimitManagement entity) {

        return LimitManagementResponse
                .builder()

                .id(
                        entity.getId())
                
                .groupName(entity.getGroupName())

                .orgId(
                        entity.getOrgId())
                
				.limitVnd(entity.getLimitVnd())
				.limitVndStr(MoneyUtils.formatMoney(entity.getLimitVnd(), "VND"))
				.limitUsd(entity.getLimitUsd())
				.limitUsdStr(MoneyUtils.formatMoney(entity.getLimitUsd(), "USD"))
				.groupLimitVnd(entity.getGroupLimitVnd())
				.groupLimitVndStr(MoneyUtils.formatMoney(entity.getGroupLimitVnd(), "VND"))
				.groupLimitUsd(entity.getGroupLimitUsd())
				.groupLimitUsdStr(MoneyUtils.formatMoney(entity.getGroupLimitUsd(), "USD"))

                .createdAt(
                        entity.getCreatedAt())

                .updatedAt(
                        entity.getUpdatedAt())

                .createdBy(
                        entity.getCreatedBy())

                .updatedBy(
                        entity.getUpdatedBy())

                .build();
    }
	
}	
