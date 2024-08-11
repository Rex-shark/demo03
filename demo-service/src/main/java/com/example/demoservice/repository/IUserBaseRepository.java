package com.example.demoservice.repository;

import com.example.demoservice.model.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserBaseRepository extends JpaRepository<UserBase, Long> {
    Optional<UserBase> findByAccount(String account);
}
