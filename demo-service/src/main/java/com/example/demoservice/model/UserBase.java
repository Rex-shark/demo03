package com.example.demoservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Table(name = "user_base")
@Data
public class UserBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(100) COMMENT '登入帳號'", unique = true)
    private String account;

    @Column(columnDefinition = " varchar(100) COMMENT '登入密碼'")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastLogin;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastLogout;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;
}
