package com.example.demoservice.repository.jpa_n1;


import com.example.demoservice.entity.jpa_n1.Child;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChildRepository extends JpaRepository<Child, Long> {

    @EntityGraph(attributePaths = { "courses","n1Info"})
    @Query("SELECT c FROM Child c")
    List<Child> findAllWithCourses();

}
