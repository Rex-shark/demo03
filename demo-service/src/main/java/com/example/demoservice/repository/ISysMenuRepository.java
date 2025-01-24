package com.example.demoservice.repository;



import com.example.demoservice.entity.SysMenu;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.request.api.SysMenuRequest;
import com.example.demoservice.request.api.SysRoleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISysMenuRepository extends JpaRepository<SysMenu, Long> {

    @Query("SELECT sm FROM SysMenu sm WHERE " +
            "(:#{#search.status} IS NULL OR sm.status = :#{#search.status}) AND " +
            "(:#{#search.nid} IS NULL OR :#{#search.nid} = '' OR sm.nid = :#{#search.nid}) AND " +
            "(:#{#search.platformName} IS NULL OR :#{#search.platformName} = '' OR sm.platformName = :#{#search.platformName}) AND " +
            "(:#{#search.menuName} IS NULL OR :#{#search.menuName} = '' OR sm.menuName LIKE %:#{#search.menuName}%)")
    Page<SysMenu> findAllBySearch(@Param("search") SysMenuRequest sysMenuRequest, Pageable pageable);

    List<SysMenu> findByStatus(Integer status);

    Boolean existsByNid(String Nid);

    void deleteByNid(String Nid);

    Optional<SysMenu> findByNid(String nid);

    @Query("SELECT sm FROM SysMenu sm WHERE sm.parentId = (SELECT m.id FROM SysMenu m WHERE m.nid = :nid)"
            + " or sm.nid = :nid")
    List<SysMenu> findMenusByParentIdForNid(@Param("nid") String nid);

}
