package com.example.demoservice.repository;

import com.example.demoservice.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISysRoleRepository extends JpaRepository<SysRole,Long> {
    Optional<SysRole> findByNid(String Nid);

}
