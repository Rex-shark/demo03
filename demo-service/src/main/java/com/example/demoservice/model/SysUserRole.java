package com.example.demoservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity(name = "SysUserRole")
@Table(name = "sys_user_role")
@EntityListeners(AuditingEntityListener.class)
public class SysUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = " bigint COMMENT '角色主鍵'")
    private Long sysRoleId;

    @Column(columnDefinition = " bigint COMMENT '用戶主鍵'")
    private Long userId;

    @Column(columnDefinition = " int COMMENT '级别'")
    private Integer level;
}
