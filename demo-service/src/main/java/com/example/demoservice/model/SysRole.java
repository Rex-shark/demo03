package com.example.demoservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@Entity(name = "SysRole")
@Table(name = "sys_role")
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = " varchar(255) COMMENT '角色名'")
    private String name;

    @Column(columnDefinition = " varchar(255) COMMENT '角色唯一標示'")
    private String nid;

    @Column(columnDefinition = " varchar(255) COMMENT '備註'")
    private String remark;

    @Column(columnDefinition = " int COMMENT '1:使用,0:刪除'")
    private Integer status;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;

    private Long createdUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;
}
