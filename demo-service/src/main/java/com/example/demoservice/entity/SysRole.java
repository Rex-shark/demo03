package com.example.demoservice.entity;

import com.example.demoservice.entity.base.AbstractEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "SysRole")
@Table(name = "sys_role")
public class SysRole extends AbstractEntity {

    @PrePersist
    private void initPrePersist() {
        super.init();
    }

    @Column(nullable = false,columnDefinition = " varchar(255) COMMENT '角色名'")
    private String name;

    @Column(nullable = false,columnDefinition = " varchar(255) COMMENT '角色唯一標示'")
    private String nid;

    @Column(columnDefinition = " varchar(255) COMMENT '備註'")
    private String remark;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<UserBase> users = new HashSet<>(); // 具有該角色的用戶集合

    @OneToMany(mappedBy = "sysRole", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SysRoleMenu> sysRoleMenus = new HashSet<>(); // 該角色所擁有的菜單集合
}
