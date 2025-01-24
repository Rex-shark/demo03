package com.example.demoservice.repository;

import com.example.demoservice.entity.UserBase;
import com.example.demoservice.request.api.UserBaseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserBaseRepository extends JpaRepository<UserBase, Long> {

    @Query("SELECT ub FROM UserBase ub WHERE " +
            "(:#{#search.status} IS NULL OR ub.status = :#{#search.status}) AND " +
            "(:#{#search.uuid} IS NULL OR :#{#search.uuid} = '' OR ub.uuid = :#{#search.uuid}) AND " +
            "(:#{#search.account} IS NULL OR :#{#search.account} = '' OR ub.account LIKE %:#{#search.account}%)")
    Page<UserBase> findAllBySearch(@Param("search") UserBaseRequest userBaseRequest, Pageable pageable);

    Page<UserBase> findByStatus(Integer status, Pageable pageable);

    Optional<UserBase> findByAccount(String account);

    Optional<UserBase> findByAccountAndStatus(String account , int status);

    Optional<UserBase> findByUuid(String uuid);

    Boolean existsByUuid(String uuid);

    void deleteByUuid(String uuid);
}
