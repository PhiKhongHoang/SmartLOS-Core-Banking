package com.ktn3.core_banking.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ktn3.core_banking.common.exception.UnauthorizedException;
import com.ktn3.core_banking.common.exception.UserNotFoundException;
import com.ktn3.core_banking.user.dto.UserResponse;
import com.ktn3.core_banking.user.entity.User;
import com.ktn3.core_banking.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public UserResponse getCurrentUser() {
    	
    	Authentication auth =
    	        SecurityContextHolder
    	                .getContext()
    	                .getAuthentication();

    	if (auth == null) {
    	    throw new UnauthorizedException();
    	}

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new UserNotFoundException());
        
        UserResponse userResponse = 
    			UserResponse.builder()
        			.id(user.getId())
        			.username(user.getUsername())
        			.fullName(user.getFullName())
        			.email(user.getEmail())
        			.active(user.getActive())
        			.build();
        
        return userResponse;        
    }
}
