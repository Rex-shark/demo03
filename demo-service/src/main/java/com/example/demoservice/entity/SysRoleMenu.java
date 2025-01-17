package com.example.demoservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity(name = "SysRoleMenu")
@Table(name = "sys_role_menu")
public class SysRoleMenu implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sys_role_id", nullable = false)
    private SysRole sysRole; // 角色

    @ManyToOne
    @JoinColumn(name = "sys_menu_id", nullable = false)
    private SysMenu sysMenu; // 菜單

    @Column(columnDefinition = "varchar(255) COMMENT '備註'")
    private String remark;
}
