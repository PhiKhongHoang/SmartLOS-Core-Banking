package com.ktn3.core_banking.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ktn3.core_banking.auth.dto.AuthResponse;
import com.ktn3.core_banking.auth.dto.LoginRequest;
import com.ktn3.core_banking.auth.dto.RefreshRequest;
import com.ktn3.core_banking.auth.dto.RegisterRequest;
import com.ktn3.core_banking.security.jwt.JwtService;
import com.ktn3.core_banking.security.service.CustomUserDetailsService;
import com.ktn3.core_banking.user.entity.Role;
import com.ktn3.core_banking.user.entity.User;
import com.ktn3.core_banking.user.repository.RoleRepository;
import com.ktn3.core_banking.user.repository.UserRepository;

import io.jsonwebtoken.Claims;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;
	
	private final CustomUserDetailsService userDetailsService;

	@Override
	@Transactional
	public void register(RegisterRequest request) {
	
	    if (userRepository.existsByUsername(
	            request.getUsername())) {
	
	        throw new RuntimeException(
	                "Username already exists");
	    }
	
	    Role role = roleRepository.findByCode(
	            request.getRoleCode())
	            .orElseThrow(() ->
	                    new RuntimeException("Role not found"));
	
	    User user = User.builder()
	            .username(request.getUsername())
	            .password(
	                    passwordEncoder.encode(
	                            request.getPassword()))
	            .fullName(request.getFullName())
	            .email(request.getEmail())
	            .active(true)
	            .build();
	
	    user.getRoles().add(role);
	
	    userRepository.save(user);
	}
	
	@Override
	public AuthResponse login(
	        LoginRequest request) {
	
	    authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    request.getUsername(),
	                    request.getPassword()));
	
	    User user = userRepository.findByUsername(request.getUsername())
	    		.orElseThrow(() -> new RuntimeException("User not found!!!") );
	
	    String token =
	            jwtService.generateToken(user);
	    
	    String resreshToken =
	    		jwtService.generateRefreshToken(user);
	
	    return AuthResponse.builder()
	            .accessToken(token)
	            .refreshToken(resreshToken)
	            .build();
	}
	
	@Override
	public AuthResponse refresh(@RequestBody RefreshRequest request) {

	    String refreshToken = request.getRefreshToken();

	    Claims claims = jwtService.parseClaims(refreshToken);

	    String userName = claims.getSubject();

	    User user = userRepository.findByUsername(userName)
	            .orElseThrow();

	    String newAccessToken = jwtService.generateToken(user);

	    return new AuthResponse(newAccessToken, refreshToken);
	}

}