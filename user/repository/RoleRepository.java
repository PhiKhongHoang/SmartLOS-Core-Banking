package com.ktn3.core_banking.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktn3.core_banking.user.entity.Role;

import java.util.Optional;

public interface RoleRepository
extends JpaRepository<Role, Long> {

Optional<Role> findByCode(String code);

}
