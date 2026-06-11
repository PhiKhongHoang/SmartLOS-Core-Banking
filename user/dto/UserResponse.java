package com.ktn3.core_banking.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {	
	private Long id;
	private String username;
	private String fullName;
	private String email;
	private Boolean active;

}
