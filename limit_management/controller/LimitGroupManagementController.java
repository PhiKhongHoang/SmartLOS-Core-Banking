package com.ktn3.core_banking.limit_management.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ktn3.core_banking.common.response.ApiResponse;
import com.ktn3.core_banking.common.response.PageResponse;
import com.ktn3.core_banking.limit_management.dto.LimitGroupManagementRequest;
import com.ktn3.core_banking.limit_management.dto.LimitGroupManagementResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementRequest;
import com.ktn3.core_banking.limit_management.dto.LimitManagementResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementSearchRequest;
import com.ktn3.core_banking.limit_management.service.LimitGroupManagementService;
import com.ktn3.core_banking.limit_management.service.LimitManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/limit-group-management")
@RequiredArgsConstructor
public class LimitGroupManagementController {

	private final LimitGroupManagementService limitGroupManagementService;

	@PutMapping("/{group-name}")
	public ResponseEntity<ApiResponse<LimitGroupManagementResponse>> update(
	        @PathVariable("group-name") String groupName,
	        @RequestBody LimitGroupManagementRequest request) {
	    return ResponseEntity
	            .status(201)
	            .body(
	                    ApiResponse
	                            .<LimitGroupManagementResponse>builder()
	                            .status(201)
	                            .message("Updated successfully")
	                            .data(limitGroupManagementService.update(groupName, request))
	                            .timestamp(LocalDateTime.now())
	                            .build()
	            );
	}
	
	@GetMapping("/{group-name}")
    public ResponseEntity<ApiResponse<LimitGroupManagementResponse>> 
				findById(@PathVariable("group-name") String groupName){

		LimitGroupManagementResponse result = limitGroupManagementService.findByGroupName(groupName);
		
		ApiResponse<LimitGroupManagementResponse> response = 
				ApiResponse.<LimitGroupManagementResponse>builder()
				.status(200)
                .message("Find by group name successfully")
                .data(result)
                .timestamp(LocalDateTime.now())
				.build();
		
		return ResponseEntity.ok(response);
    }
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<LimitGroupManagementResponse>>> search(
			@RequestBody LimitManagementSearchRequest request,
	        @PageableDefault(size = 10) Pageable pageable) {

	    PageResponse<LimitGroupManagementResponse> result =
	    		limitGroupManagementService.search(request, pageable);

	    ApiResponse<PageResponse<LimitGroupManagementResponse>> response =
	            ApiResponse.<PageResponse<LimitGroupManagementResponse>>builder()
	                    .status(200)
	                    .message("Search successfully")
	                    .data(result)
	                    .timestamp(LocalDateTime.now())
	                    .build();

	    return ResponseEntity.ok(response);
	}
	
}
