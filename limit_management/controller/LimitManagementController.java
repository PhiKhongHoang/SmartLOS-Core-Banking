package com.ktn3.core_banking.limit_management.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktn3.core_banking.common.response.ApiResponse;
import com.ktn3.core_banking.common.response.PageResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementRequest;
import com.ktn3.core_banking.limit_management.dto.LimitManagementResponse;
import com.ktn3.core_banking.limit_management.dto.LimitManagementSearchRequest;
import com.ktn3.core_banking.limit_management.service.LimitManagementService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/limit-management")
@RequiredArgsConstructor
public class LimitManagementController {
	private final LimitManagementService limitManagementService;
	
	@PostMapping("")
	public ResponseEntity<ApiResponse<LimitManagementResponse>> update( @RequestBody LimitManagementRequest request){
	    return ResponseEntity
	            .status(201)
	            .body(
	                    ApiResponse
	                            .<LimitManagementResponse>builder()
	                            .status(201)
	                            .message("Updated successfully")
	                            .data(limitManagementService.update(request))
	                            .timestamp(LocalDateTime.now())
	                            .build()
	            );
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LimitManagementResponse>> findById(@PathVariable Long id){

		LimitManagementResponse result = limitManagementService.findById(id);
		
		ApiResponse<LimitManagementResponse> response = 
				ApiResponse.<LimitManagementResponse>builder()
				.status(200)
                .message("Find by id successfully")
                .data(result)
                .timestamp(LocalDateTime.now())
				.build();
		
		return ResponseEntity.ok(response);
    }
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<LimitManagementResponse>>> search(
			@RequestBody LimitManagementSearchRequest request,
	        @PageableDefault(size = 10) Pageable pageable) {

	    PageResponse<LimitManagementResponse> result =
	            limitManagementService.search(request, pageable);

	    ApiResponse<PageResponse<LimitManagementResponse>> response =
	            ApiResponse.<PageResponse<LimitManagementResponse>>builder()
	                    .status(200)
	                    .message("Search successfully")
	                    .data(result)
	                    .timestamp(LocalDateTime.now())
	                    .build();

	    return ResponseEntity.ok(response);
	}
	
}
