package com.ktn3.core_banking.org.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgUnitRequest {
    private String name;
    private String type; // HO / SO / PGD

    private Long parentId;
}
