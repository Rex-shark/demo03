package com.example.demoservice.repository;

import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISysRoleMenuRepository extends JpaRepository<SysRoleMenu,Long> {

    /*
        這刪除方式無效 待研究
        FIXME 暫時解決，語句結束後要加上entityManager.flush();，待研究原因
     */
    void deleteBySysRole(SysRole sysRole);

}
