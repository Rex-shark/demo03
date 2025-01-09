package com.example.demoapi.contorller;

import com.example.demoservice.entity.OrderDemo;
import com.example.demoservice.service.service.OrderDemoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderDemoService orderDemoService;

    // 新增訂單
    @PostMapping
    public ResponseEntity<OrderDemo> createOrder(@RequestBody OrderDemo orderDemo) {
        System.out.println("新增訂單 orderDemo : " + orderDemo);
        OrderDemo createdOrder = orderDemoService.createOrder(orderDemo);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // 查詢所有訂單
    @GetMapping
    public ResponseEntity<List<OrderDemo>> getAllOrders() {
        System.out.println("查詢所有訂單");
        List<OrderDemo> orders = orderDemoService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 查詢單個訂單
    @GetMapping("/{id}")
    public ResponseEntity<OrderDemo> getOrderById(@PathVariable Long id) {
        System.out.println("查詢單個訂單 id = " + id);
        Optional<OrderDemo> order = orderDemoService.getOrderById(id);
        return order.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 更新訂單
    @PutMapping("/{uuid}")
    public ResponseEntity<OrderDemo> updateOrder(@PathVariable String uuid, @RequestBody OrderDemo orderDemo) throws IllegalAccessException {
        System.out.println("更新訂單 uuid = " + uuid);
        OrderDemo updatedOrder = orderDemoService.updateOrderByUuid(uuid, orderDemo);
        return updatedOrder != null ?
                new ResponseEntity<>(updatedOrder, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 刪除訂單
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String uuid) {
        System.out.println("刪除訂單 uuid = " + uuid);
        orderDemoService.deleteOrderByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}