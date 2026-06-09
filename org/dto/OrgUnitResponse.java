package com.ktn3.core_banking.org.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class OrgUnitResponse {

    private Long id;
    private String code;
    private String name;
    private String type;

    private Long parentId;

    private List<OrgUnitResponse> children;
}
