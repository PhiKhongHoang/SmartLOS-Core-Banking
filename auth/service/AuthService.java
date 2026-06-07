package com.ktn3.core_banking.auth.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.ktn3.core_banking.auth.dto.AuthResponse;
import com.ktn3.core_banking.auth.dto.LoginRequest;
import com.ktn3.core_banking.auth.dto.RefreshRequest;
import com.ktn3.core_banking.auth.dto.RegisterRequest;

public interface AuthService {

	void register(RegisterRequest request);
	
	AuthResponse login(LoginRequest request);

	AuthResponse refresh(@RequestBody RefreshRequest request);
}
