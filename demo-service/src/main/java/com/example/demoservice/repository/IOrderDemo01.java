package com.example.demoservice.repository;

import com.example.demoservice.model.OrderDemo01;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDemo01 extends JpaRepository<OrderDemo01,Long> {
}
