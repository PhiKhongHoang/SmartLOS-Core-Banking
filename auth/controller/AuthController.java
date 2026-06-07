package com.ktn3.core_banking.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktn3.core_banking.auth.dto.AuthResponse;
import com.ktn3.core_banking.auth.dto.LoginRequest;
import com.ktn3.core_banking.auth.dto.RefreshRequest;
import com.ktn3.core_banking.auth.dto.RegisterRequest;
import com.ktn3.core_banking.auth.service.AuthService;
import com.ktn3.core_banking.user.entity.User;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/register")
	public String register(
	        @Valid
	        @RequestBody RegisterRequest request) {
	
	    authService.register(request);
	
	    return "Register success";
	}
	
	@PostMapping("/login")
	public AuthResponse login(
	        @Valid
	        @RequestBody LoginRequest request) {
	
	    return authService.login(request);
	}
	
	@PostMapping("/refresh")
	public AuthResponse refresh(@RequestBody RefreshRequest request) {
		return authService.refresh(request);
	}

}
