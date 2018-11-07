package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
