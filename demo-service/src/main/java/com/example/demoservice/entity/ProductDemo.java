package com.example.demoservice.entity;

import com.example.demoservice.entity.base.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_demo")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ProductDemo extends AbstractEntity {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false, length = 36, unique = true)
    private String uuid;

    @Column(nullable = false, length = 200)
    private String productName;  // 商品名稱

    @Column(nullable = false)
    private Double price;  // 商品價格

    @Version
    @Column(nullable = false)
    private Integer ver;  // 版本號

    @PrePersist
    private void initPrePersist() {
        super.init();
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();  // 生成 UUID
        }
    }
}
