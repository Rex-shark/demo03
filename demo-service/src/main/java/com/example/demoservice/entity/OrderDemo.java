package com.example.demoservice.entity;

import com.example.demoservice.entity.base.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_demo")
@Data
@EntityListeners(AuditingEntityListener.class)
public class OrderDemo  extends AbstractEntity {

    @NotBlank(message = "uuid is mandatory")
    @Size(min = 36, max = 36, message = "uuid size is 36")
    @Column(updatable = false, nullable = false, length = 36, unique = true , columnDefinition = "VARCHAR(36) COMMENT 'uuid'")
    private String uuid;

    @NotBlank(message = "orderName is mandatory")
    @Size(min = 1, max = 200, message = "orderName must be between 1 and 20 characters")
    @Column(nullable = false, length = 200, columnDefinition = "VARCHAR(200) COMMENT '訂單名稱'")
    private String orderName;

    @PrePersist
    private void initPrePersist() {
        super.init();
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();  // 生成 UUID
        }
    }
}
