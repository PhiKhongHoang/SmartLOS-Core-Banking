package com.ktn3.core_banking.limit_management.dto;

import lombok.Data;

@Data
public class LimitManagementSearchRequest {

    private String groupName;
    private Long orgId;
}
