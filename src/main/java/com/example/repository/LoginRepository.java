package com.example.repository;

import com.example.domain.enums.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginRepository extends JpaRepository<Login, String> {
    UserDetails findByLogin(String login);
}
