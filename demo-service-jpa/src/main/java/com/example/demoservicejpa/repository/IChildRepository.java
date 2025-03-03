package com.example.demoservicejpa.repository;



import com.example.demoservicejpa.entity.Child;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChildRepository extends JpaRepository<Child, Long> {

    @EntityGraph(attributePaths = { "courses","userInfo"})
    @Query("SELECT c FROM Child c")
    List<Child> findAllWithCourses();

}
