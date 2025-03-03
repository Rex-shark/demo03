package com.example.demoapijpa.contorller;

import com.example.demoservice.entity.jpa_n1.Child;
import com.example.demoservice.entity.jpa_n1.Course;
import com.example.demoservice.repository.jpa_n1.IChildRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
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
@RequestMapping("/test/n1")
public class N1Controller {

    @Resource
    IChildRepository childRepository;

    @GetMapping("/test")
    public ResponseEntity<?> test() throws JsonProcessingException {
        System.out.println("test");
        ObjectMapper objectMapper = new ObjectMapper();

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
