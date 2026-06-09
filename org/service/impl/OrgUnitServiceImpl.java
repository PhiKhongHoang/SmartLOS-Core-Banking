package com.ktn3.core_banking.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ktn3.core_banking.common.exception.BusinessException;
import com.ktn3.core_banking.org.constant.OrgType;
import com.ktn3.core_banking.org.dto.OrgUnitRequest;
import com.ktn3.core_banking.org.dto.OrgUnitResponse;
import com.ktn3.core_banking.org.entity.OrgUnit;
import com.ktn3.core_banking.org.repository.OrgUnitRepository;
import com.ktn3.core_banking.org.service.OrgUnitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrgUnitServiceImpl implements OrgUnitService {
	
	private final OrgUnitRepository orgUnitRepository;

	@Override
	public OrgUnitResponse create(OrgUnitRequest request) {		
		OrgType type;
		try {
		    type = OrgType.valueOf(request.getType().toUpperCase());
		} catch (IllegalArgumentException e) {
		    throw new IllegalArgumentException(
		            "Org type không hợp lệ. Chỉ chấp nhận: HO, SO, PGD");
		}
		
		OrgUnit parent = null;
        if (request.getParentId() != null) {
            parent = orgUnitRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent not found"));
        }
		
		String code = "";
		Integer codeInt = 0;
		if("SO".equals(type.toString())) {
			code = orgUnitRepository.findMaxCodeSO();
			try {
				codeInt = Integer.valueOf(code);
				code = String.valueOf(codeInt + 100); // nếu là SO tăng lên 100 mỗi đơn vị
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("Mã code SO không hợp lệ");
			}
		}else if("PGD".equals(type.toString())) {
			code = orgUnitRepository.findMaxCodeByParentId(parent.getId());
			try {
				codeInt = Integer.valueOf(code);
				code = String.valueOf(codeInt + 1); // nếu là PGD tăng lên 1 mỗi đơn vị
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("Mã code PGD không hợp lệ");
			}
		}
        
        OrgUnit orgUnit = OrgUnit.builder()
        		.type(type)
        		.code(code)
        		.name(request.getName())
        		.parent(parent)
        		.build();
        orgUnit = orgUnitRepository.save(orgUnit);
		
		return OrgUnitResponse.builder()
				.id(orgUnit.getId())
				.code(orgUnit.getCode())
				.name(orgUnit.getName())
				.type(orgUnit.getType().toString())
				.parentId(orgUnit.getParent().getId())
				.build();
	}

}
