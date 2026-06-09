package com.ktn3.core_banking.org.service;

import com.ktn3.core_banking.org.dto.OrgUnitRequest;
import com.ktn3.core_banking.org.dto.OrgUnitResponse;

public interface OrgUnitService {
	OrgUnitResponse create(OrgUnitRequest request);
}
