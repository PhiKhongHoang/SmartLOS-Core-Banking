package com.ktn3.core_banking.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.ktn3.core_banking.security.model.CustomUserPrincipal;
import com.ktn3.core_banking.user.entity.User;
import com.ktn3.core_banking.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "User not found"));

        return new CustomUserPrincipal(user);
    }
}
