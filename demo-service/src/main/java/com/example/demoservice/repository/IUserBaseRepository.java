package com.example.demoservice.repository;

import com.example.demoservice.entity.OrderDemo;
import com.example.demoservice.entity.ProductDemo;
import com.example.demoservice.entity.UserBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserBaseRepository extends JpaRepository<UserBase, Long> {
    Optional<UserBase> findByAccount(String account);

    Page<UserBase> findByStatus(Integer status, Pageable pageable);

    Optional<UserBase> findByUuid(String uuid);
}
