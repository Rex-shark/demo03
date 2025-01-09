package com.example.demoservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "SysRoleMenu")
@Table(name = "sys_role_menu")
public class SysRoleMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = " bigint COMMENT '角色主鍵'")
    private Long sysRoleId;

    @Column(columnDefinition = " bigint COMMENT '菜單主鍵'")
    private Long sysMenuId;
}
