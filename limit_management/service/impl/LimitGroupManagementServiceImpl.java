package com.ktn3.core_banking.limit_management.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktn3.core_banking.common.response.PageResponse;
import com.ktn3.core_banking.common.util.MoneyUtils;
import com.ktn3.core_banking.limit_management.dto.LimitGroupManagementRequest;
import com.ktn3.core_banking.limit_management.dto.LimitGroupManagementResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementSearchRequest;
import com.ktn3.core_banking.limit_management.entity.LimitManagement;
import com.ktn3.core_banking.limit_management.repository.LimitManagementRepository;
import com.ktn3.core_banking.limit_management.service.LimitGroupManagementService;
import com.ktn3.core_banking.org.dto.OrgIdNameResponse;
import com.ktn3.core_banking.org.repository.OrgUnitRepository;
import com.ktn3.core_banking.security.service.CurrentUserService;
import com.ktn3.core_banking.user.dto.UserResponse;

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
			
			// b2: tạo all list mới, set value thành value mới
			if(request != null
					&& request.getOrgIds() != null
					&& request.getOrgIds().size() > 0) {
				for(Long id : request.getOrgIds()) {
					LimitManagement limitManagement = limitManagementRepository.findById(id)
							.orElse(null);
					if(limitManagement != null) {
						limitManagement.setGroupName(groupName);
						limitManagement.setLimitVnd(request.getGroupLimitVnd());
						limitManagement.setGroupLimitVnd(request.getGroupLimitVnd());
						limitManagement.setLimitUsd(request.getGroupLimitUsd());
						limitManagement.setGroupLimitUsd(request.getGroupLimitUsd());
						limitManagement.setUpdatedAt(LocalDateTime.now());
						limitManagement.setUpdatedBy(userResponse.getId());
						
						limitManagement = limitManagementRepository.save(limitManagement);
					}
					
				}
			}
			
			List<OrgIdNameResponse> orgResponses = new ArrayList<>();

			for (Long id : request.getOrgIds()) {
			    String orgName = orgUnitRepository.findNameById(id);

			    OrgIdNameResponse response = new OrgIdNameResponse();
			    response.setId(id);
			    response.setName(orgName);

			    orgResponses.add(response);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResponse<LimitGroupManagementResponse> search(LimitManagementSearchRequest request, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
