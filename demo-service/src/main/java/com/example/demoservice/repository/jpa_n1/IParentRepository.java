package com.example.demoservice.repository.jpa_n1;

import com.example.demoservice.entity.jpa_n1.Parent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IParentRepository extends JpaRepository<Parent, Long> {

    //attributePaths 用來定義要被同時關聯的屬性，有定義才查
    @EntityGraph(attributePaths = "children")
    @Query("SELECT p FROM Parent p")
    List<Parent> findAllWithChildren();

    //這樣只查一次
    @EntityGraph(attributePaths = { "n1Info","children","children.n1Info"})
    @Query("SELECT p FROM Parent p")
    List<Parent> findAllWithChildrenAndN1Info();

    // @BatchSize (適用於 @OneToMany)  適用於大量數據
}