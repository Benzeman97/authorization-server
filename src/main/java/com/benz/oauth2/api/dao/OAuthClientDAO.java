package com.benz.oauth2.api.dao;

import com.benz.oauth2.api.entity.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthClientDAO extends JpaRepository<OAuthClient,String> {

    Optional<OAuthClient> findByClientId(String clientId);
}
