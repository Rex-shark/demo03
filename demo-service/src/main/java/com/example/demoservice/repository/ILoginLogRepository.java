package com.example.demoservice.repository;

import com.example.demoservice.entity.log.LoginLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoginLogRepository extends JpaRepository<LoginLogEntity, Long> {
}
