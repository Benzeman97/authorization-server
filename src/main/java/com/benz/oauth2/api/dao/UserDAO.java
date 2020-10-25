package com.benz.oauth2.api.dao;

import com.benz.oauth2.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {

    Optional<User> findByUserName(String userName);

    Optional<User> existsByUserName(String userName);
}
