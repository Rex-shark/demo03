package com.example.demoservice.service.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoservice.entity.OrderDemo;
import com.example.demoservice.repository.IOrderDemoRepository;
import com.example.demoservice.utils.ReflectionUtils;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    /**
     * 更新指定 UUID 的訂單資訊。
     * <p>
     * 此方法會根據傳入的 UUID 查找訂單，然後使用 `ReflectionUtils.copyNonNullProperties`
     * 方法來更新非空屬性，並記錄更新者的 ID。
     * @param uuid      訂單的唯一識別碼
     * @param orderDemo 包含更新資料的訂單物件
     * @return 更新後的 `OrderDemo` 物件，若找不到訂單則回傳 `null`
     * @throws IllegalAccessException 可能會在反射操作時拋出
     */
    public OrderDemo updateOrderByUuid(String uuid, OrderDemo orderDemo) throws IllegalAccessException {
        System.out.println("orderDemo.getOrderName() = " + orderDemo.getOrderName());
        System.out.println("uuid = " + uuid);
        Optional<OrderDemo> orderDemoOptional = orderDemoRepository.findByUuid(uuid);
        if (orderDemoOptional.isEmpty()) {
            System.out.println("updateOrderByUuid " );
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

    }


    public Page<OrderDemo> getOrders(int page, int size,  int status) {
        Pageable pageable = PageRequest.of(page, size);
        return orderDemoRepository.findByStatus(status, pageable);
    }
}