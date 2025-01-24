package com.example.demoapi.contorller;

import com.example.demoservice.entity.TestEntity;
import com.example.demoservice.repository.ITestEntityRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/test/entity")
public class TestEntityController {

    @Resource
    ITestEntityRepository testEntityRepository;



    // 查詢所有訂單
    @GetMapping
    public ResponseEntity<List<TestEntity>> getAllOrders() {
        System.out.println("查詢所有TEST訂單");
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        for (int i = 0; i < testEntityList.size(); i++) {
            System.out.println("testEntityList.get(i).getStatus() = " + testEntityList.get(i).getStatus());
        }

        return new ResponseEntity<>(testEntityList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TestEntity> createOrder(@RequestBody TestEntity testEntity) {
        System.out.println("新增訂單 testEntity : " + testEntity);
        TestEntity e = testEntityRepository.save(testEntity);
        return new ResponseEntity<>(e, HttpStatus.CREATED);
    }
}