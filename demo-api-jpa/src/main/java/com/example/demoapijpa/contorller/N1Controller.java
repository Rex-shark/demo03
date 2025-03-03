package com.example.demoapijpa.contorller;


import com.example.demoservicejpa.entity.*;
import com.example.demoservicejpa.repository.IChildRepository;
import com.example.demoservicejpa.repository.IParentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/n1")
public class N1Controller {

    @Resource
    IParentRepository parentRepository;

    @Resource
    IChildRepository childRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<List<Parent>> getAll() throws JsonProcessingException {
        System.out.println("N+1 ");
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("--------");
        List<Parent> parents = parentRepository.findAll();  // 這裡只執行 1 次 SQL
        for (Parent parent : parents) {
            String json = objectMapper.writeValueAsString(parent);
            System.out.println(json);  // 這裡會額外查詢 N 次
        }

        entityManager.clear();
        System.out.println("********");
        List<Parent> parents2 = parentRepository.findAllWithChildren();
        for (Parent parent : parents2) {
            String json = objectMapper.writeValueAsString(parent);
            System.out.println(json);
        }

        entityManager.clear();
        System.out.println("++++++++++");
        List<Parent> parents3 = parentRepository.findAllWithChildrenAndN1Info();
        for (Parent parent : parents3) {
//            String json = objectMapper.writeValueAsString(parent);
//            System.out.println(json);
            System.out.println("parent = " + parent);
        }

        return new ResponseEntity<>(parents3, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        System.out.println("test");

        List<Child> children = childRepository.findAllWithCourses();
        for (Child child : children) {
            System.out.println("小孩: " + child.getName());
            for (Course course : child.getCourses()) {
                System.out.println("  - 修課: " + course.getTitle());
            }
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
