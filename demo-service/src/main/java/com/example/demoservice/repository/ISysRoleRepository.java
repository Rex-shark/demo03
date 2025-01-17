package com.example.demoservice.repository;

import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.UserBase;
import com.example.demoservice.request.user.SysRoleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ISysRoleRepository extends JpaRepository<SysRole,Long> {
    Optional<SysRole> findByNid(String Nid);

    @Query("SELECT sr FROM SysRole sr WHERE " +
            "(:#{#search.status} IS NULL OR sr.status = :#{#search.status}) AND " +
            "(:#{#search.nid} IS NULL OR :#{#search.nid} = ''OR sr.nid = :#{#search.nid})")
    Page<SysRole> findAllBySearch(@Param("search") SysRoleRequest sysRoleRequest, Pageable pageable);


//    @Query("SELECT sr FROM SysRole sr WHERE " +
//            "(COALESCE(:#{#sysRoleRequest.status}, sr.status) = sr.status)")
//    Page<SysRole> findAllBySearch(@Param("sysRoleRequest") SysRoleRequest sysRoleRequest, Pageable pageable);
//
//    @Query("SELECT sr FROM SysRole sr WHERE " +
//            "(:status IS NULL OR sr.status = :status)")
//    Page<SysRole> findAllBySearch(@Param("status") String status, Pageable pageable);


}
