package com.example.demoservice.repository;



import com.example.demoservice.entity.SysMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISysMenuRepository extends JpaRepository<SysMenu, Long> {

//    @Query("SELECT sm FROM UserBase ub " +
//            " LEFT JOIN SysUserRole sur  on ub.id = sur.userId " +
//            " LEFT JOIN SysRole sr  on sr.id = sur.sysRoleId " +
//            " LEFT JOIN SysRoleMenu srm  on srm.sysRoleId = sr.id " +
//            " LEFT JOIN SysMenu sm  on sm.id = srm.sysMenuId " +
//            " WHERE sr.status = 1 " +
//            " and sm.status = 1" +
//            " and ub.account = ?1" +
//            " and sm.platformName = ?2"
//    )
//    List<SysMenu> findUserMenuList(String account,String platformName);

    List<SysMenu> findByStatus(Integer status);


    Optional<SysMenu> findByNid(String nid);

    @Query("SELECT sm FROM SysMenu sm WHERE sm.parentId = (SELECT m.id FROM SysMenu m WHERE m.nid = :nid)"
            + " or sm.nid = :nid")
    List<SysMenu> findMenusByParentIdForNid(@Param("nid") String nid);

}
