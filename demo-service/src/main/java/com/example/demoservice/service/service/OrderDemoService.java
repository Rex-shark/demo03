package com.example.demoservice.service.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoservice.entity.OrderDemo;
import com.example.demoservice.repository.IOrderDemoRepository;
import com.example.demoservice.util.ReflectionUtils;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDemoService {

    @Resource
    private IOrderDemoRepository orderDemoRepository;

    // 新增訂單
    public OrderDemo createOrder(OrderDemo orderDemo) {
        return orderDemoRepository.save(orderDemo);
    }

    // 查詢所有訂單
    public List<OrderDemo> getAllOrders() {
        return orderDemoRepository.findAll();
    }

    // 查詢訂單
    public Optional<OrderDemo> getOrderById(Long id) {
        return orderDemoRepository.findById(id);
    }

    // 更新訂單
    public OrderDemo updateOrder(Long id, OrderDemo orderDemo) {
        if (orderDemoRepository.existsById(id)) {
            orderDemo.setId(id);
            return orderDemoRepository.save(orderDemo);
        }
        return null;  // 或可以拋出自定義異常
    }

    // 更新訂單
    public OrderDemo updateOrderByUuid(String uuid, OrderDemo orderDemo) throws IllegalAccessException {
        System.out.println("orderDemo.getOrderName() = " + orderDemo.getOrderName());
        Optional<OrderDemo> orderDemoOptional = orderDemoRepository.findByUuid(uuid);
        if (orderDemoOptional.isEmpty()) {
            return null;  // 或可以拋出自定義異常
        }
        OrderDemo oldOrder = orderDemoOptional.get();
        ReflectionUtils.copyNonNullProperties(orderDemo, oldOrder);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof DecodedJWT jwt) {
                oldOrder.setUpdateUserId(jwt.getClaim("num").asLong());
            }
        }else {
            oldOrder.setUpdateUserId(1L);
        }

        return orderDemoRepository.save(oldOrder);
    }

    // 刪除訂單
    public void deleteOrder(Long id) {
        if (orderDemoRepository.existsById(id)) {
            orderDemoRepository.deleteById(id);
        }
    }

    // 刪除訂單
    public void deleteOrderByUuid(String uuid) {
        Optional<OrderDemo> orderDemoOptional = orderDemoRepository.findByUuid(uuid);
        orderDemoOptional.ifPresent(orderDemo -> orderDemoRepository.deleteById(orderDemo.getId()));
    }
}