package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.AccessTokenEntity;

public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String>{

}
