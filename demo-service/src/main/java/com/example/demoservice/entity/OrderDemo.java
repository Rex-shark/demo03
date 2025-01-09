package com.example.demoservice.entity;

import com.example.demoservice.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "order_demo")
@Data
@EntityListeners(AuditingEntityListener.class)
public class OrderDemo  {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false, length = 36, unique = true)
    private String uuid;

    @Column(nullable = false, length = 200)
    private String orderName;  // 訂單名稱

    @JsonIgnore
    @Column(nullable = false)
    private Integer status;  // 狀態

    @JsonIgnore
    @Column(nullable = false)
    private Long createdUserId;

    @JsonIgnore
    @Column()
    private Long updateUserId;

    @JsonIgnore
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @JsonIgnore
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;

    @PrePersist
    private void initPrePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();  // 生成 UUID
        }
        if(this.status == null) {
            this.status = 1 ;
        }
        if(this.createdUserId == null) {
            this.createdUserId = 1L ;
        }
    }
}
