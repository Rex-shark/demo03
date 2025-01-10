package com.example.demoservice.repository;

import com.example.demoservice.entity.ProductDemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductDemoRepository extends JpaRepository<ProductDemo, Long> {

    Optional<ProductDemo> findByUuid(String uuid);
}