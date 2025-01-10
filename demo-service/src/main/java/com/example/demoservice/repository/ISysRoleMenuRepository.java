package com.example.demoservice.repository;

import com.example.demoservice.entity.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISysRoleMenuRepository extends JpaRepository<SysRoleMenu,Long> {

}
