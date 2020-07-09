package com.smoothstack.lms.orchestration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoothstack.lms.orchestration.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);
}