package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.AccessTokenEntity;
import com.rft.entities.RefreshTokenEntity;

import java.lang.String;
import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String>{
	RefreshTokenEntity findByTokenId(String tokenid);
}
