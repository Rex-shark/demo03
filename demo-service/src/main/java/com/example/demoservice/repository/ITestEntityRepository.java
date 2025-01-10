package com.example.demoservice.repository;

import com.example.demoservice.entity.TestEntity;
import com.example.demoservice.entity.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITestEntityRepository extends JpaRepository<TestEntity, Long> {
    Optional<TestEntity> findByOrderName(String orderName);
}
