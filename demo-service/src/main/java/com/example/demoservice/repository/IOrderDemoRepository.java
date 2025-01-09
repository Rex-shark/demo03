package com.example.demoservice.repository;

import com.example.demoservice.entity.OrderDemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderDemoRepository extends JpaRepository<OrderDemo, Long> {
    // 您可以在這裡添加其他自定義查詢方法，例如：
    // List<OrderDemo> findByOrderName(String orderName);

    Optional<OrderDemo> findByUuid(String uuid);
}