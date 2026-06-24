package com.ktn3.core_banking.limit_management.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktn3.core_banking.common.response.PageResponse;
import com.ktn3.core_banking.common.util.MoneyUtils;
import com.ktn3.core_banking.limit_management.dto.LimitGroupManagementRequest;
import com.ktn3.core_banking.limit_management.dto.LimitGroupManagementResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementSearchRequest;
import com.ktn3.core_banking.limit_management.entity.LimitManagement;
import com.ktn3.core_banking.limit_management.repository.LimitManagementRepository;
import com.ktn3.core_banking.limit_management.service.LimitGroupManagementService;
import com.ktn3.core_banking.org.dto.OrgIdNameResponse;
import com.ktn3.core_banking.org.entity.OrgUnit;
import com.ktn3.core_banking.org.repository.OrgUnitRepository;
import com.ktn3.core_banking.security.service.CurrentUserService;
import com.ktn3.core_banking.user.dto.UserResponse;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LimitGroupManagementServiceImpl implements LimitGroupManagementService {
	
	private final LimitManagementRepository limitManagementRepository;
	private final CurrentUserService currentUserService;
	private final OrgUnitRepository orgUnitRepository;

	@Override
	@Transactional
	public LimitGroupManagementResponse update(String groupName, LimitGroupManagementRequest request) {
		try {
			UserResponse userResponse = currentUserService.getCurrentUser();
			
			// b1: xóa all list cũ
			limitManagementRepository.deleteAllByGroupName(groupName);
			
			// b2: tạo all list mới
			List<OrgIdNameResponse> orgResponses = new ArrayList<>();
			
			if(request != null
					&& request.getOrgIds() != null
					&& request.getOrgIds().size() > 0) {
				for(Long id : request.getOrgIds()) {
					String orgName = orgUnitRepository.findNameById(id);
					
					if(orgName != null && !"".equals(orgName)) {
						OrgIdNameResponse response = new OrgIdNameResponse();
					    response.setId(id);
					    response.setName(orgName);
						
					    orgResponses.add(response);
					    
						LimitManagement limitManagement = LimitManagement
								.builder()
								.orgId(id)
								.groupName(groupName)
								.limitVnd(request.getGroupLimitVnd())
								.groupLimitVnd(request.getGroupLimitVnd())
								.limitUsd(request.getGroupLimitUsd())
								.groupLimitUsd(request.getGroupLimitUsd())
								.updatedAt(LocalDateTime.now())
								.updatedBy(userResponse.getId())
								.build();

						limitManagement = limitManagementRepository.save(limitManagement);
					}
					
				}
			}
			
			LimitGroupManagementResponse limitGroupManagementResponse = 
					LimitGroupManagementResponse.builder()
						.groupName(groupName)
						.orgResponses(orgResponses)
						.groupLimitVnd(request.getGroupLimitVnd())
						.groupLimitVndStr(MoneyUtils.formatMoney(request.getGroupLimitVnd(), "VND"))
						.groupLimitUsd(request.getGroupLimitUsd())
						.groupLimitUsdStr(MoneyUtils.formatMoney(request.getGroupLimitUsd(), "USD"))
						.updatedAt(LocalDateTime.now())
						.updatedBy(userResponse.getId())
						.build();
			
			return limitGroupManagementResponse;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public LimitGroupManagementResponse findByGroupName(String groupName) {
		try {
			List<LimitManagement> limitManagements = 
					limitManagementRepository.findByGroupName(groupName);
			
			List<OrgIdNameResponse> orgResponses = new ArrayList<>();
			for(LimitManagement item : limitManagements) {
				OrgUnit orgUnit = orgUnitRepository.findById(item.getOrgId()).orElse(null);
				
				if(orgUnit != null) {
					OrgIdNameResponse orgIdNameResponse = new OrgIdNameResponse();
					
					orgIdNameResponse.setId(orgUnit.getId());
					orgIdNameResponse.setName(orgUnit.getName());
					
					orgResponses.add(orgIdNameResponse);
				}
			}
			
			LimitGroupManagementResponse response = LimitGroupManagementResponse
					.builder()
					.groupName(groupName)
					.orgResponses(orgResponses)
					.groupLimitVnd(limitManagements.get(0).getGroupLimitVnd())
					.groupLimitVndStr(MoneyUtils.formatMoney(limitManagements.get(0).getGroupLimitVnd(), "VND"))
					.groupLimitUsd(limitManagements.get(0).getGroupLimitUsd())
					.groupLimitUsdStr(MoneyUtils.formatMoney(limitManagements.get(0).getGroupLimitUsd(), "USD"))
					.createdAt(limitManagements.get(0).getCreatedAt())
					.updatedAt(limitManagements.get(0).getUpdatedAt())
					.createdBy(limitManagements.get(0).getCreatedBy())
					.updatedBy(limitManagements.get(0).getUpdatedBy())
					.build();
			
			return response;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResponse<LimitGroupManagementResponse> search(LimitManagementSearchRequest request, Pageable pageable) {
		try {
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

	    Map<String, List<LimitManagement>> grouped =
	            page.getContent()
	                .stream()
	                .collect(Collectors.groupingBy(
	                        LimitManagement::getGroupName
	                ));
	    
	    List<LimitGroupManagementResponse> responses = new ArrayList<>();

	    for (Map.Entry<String, List<LimitManagement>> entry : grouped.entrySet()) {

	        List<LimitManagement> items = entry.getValue();

	        LimitManagement first = items.get(0);

	        List<OrgIdNameResponse> orgResponses = new ArrayList<>();

	        for (LimitManagement item : items) {

	            Long orgId = item.getOrgId();

	            OrgIdNameResponse org = new OrgIdNameResponse();
	            org.setId(orgId);
	            org.setName(orgUnitRepository.findNameById(orgId));

	            orgResponses.add(org);
	        }

	        LimitGroupManagementResponse response =
	                LimitGroupManagementResponse.builder()
	                        .groupName(first.getGroupName())
	                        .orgResponses(orgResponses)

	                        .groupLimitVnd(first.getGroupLimitVnd())
	                        .groupLimitVndStr(
	                                MoneyUtils.formatMoney(
	                                        first.getGroupLimitVnd(),
	                                        "VND"
	                                )
	                        )

	                        .groupLimitUsd(first.getGroupLimitUsd())
	                        .groupLimitUsdStr(
	                                MoneyUtils.formatMoney(
	                                        first.getGroupLimitUsd(),
	                                        "USD"
	                                )
	                        )

	                        .createdBy(first.getCreatedBy())
	                        .createdAt(first.getCreatedAt())
	                        .updatedBy(first.getUpdatedBy())
	                        .updatedAt(first.getUpdatedAt())

	                        .build();

	        responses.add(response);
	    }
	    
	    return PageResponse.<LimitGroupManagementResponse>
			    builder()
			.items(responses)			
			.page(page.getNumber())
			.size(page.getSize())
			.totalElements(page.getTotalElements())
			.totalPages(page.getTotalPages())
			.build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return null;
	}

}
