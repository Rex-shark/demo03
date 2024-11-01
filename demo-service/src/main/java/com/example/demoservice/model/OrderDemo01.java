package com.example.demoservice.model;

import jakarta.persistence.*;
import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "order_demo01")
@Data
@EntityListeners(AuditingEntityListener.class)
public class OrderDemo01 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false, length = 36, unique = true)
    private String uuid;

    @Column(nullable = false, length = 200)
    private String orderName;  // 訂單名稱

    @Column(nullable = false)
    private Integer status;  // 狀態

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;

    @PrePersist
    private void generateId() {
        this.uuid = UUID.randomUUID().toString();
    }
}
