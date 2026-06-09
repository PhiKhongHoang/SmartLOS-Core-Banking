package com.ktn3.core_banking.org.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktn3.core_banking.org.dto.OrgUnitRequest;
import com.ktn3.core_banking.org.dto.OrgUnitResponse;
import com.ktn3.core_banking.org.service.OrgUnitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orgs")
@RequiredArgsConstructor
public class OrgUnitController {
	
	private final OrgUnitService orgUnitService;

	@PostMapping("")
	public OrgUnitResponse create(@RequestBody OrgUnitRequest request) {
        return orgUnitService.create(request);
    } 
	
}
