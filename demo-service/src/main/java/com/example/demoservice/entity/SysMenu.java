package com.example.demoservice.entity;

import com.example.demoservice.entity.base.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "SysMenu")
@Table(name = "sys_menu")
public class SysMenu extends AbstractEntity {

    @PrePersist
    private void initPrePersist() {
        super.init();
    }

    @Column(nullable = false,columnDefinition = " varchar(255) COMMENT '唯一標示'")
    private String nid;

    @Column(nullable = false,columnDefinition = " varchar(255) COMMENT '菜單名稱'")
    private String menuName;

    @Column(nullable = false,columnDefinition = " varchar(255) COMMENT '平台名稱'")
    private String platformName;

    @Column(nullable = false,columnDefinition = " bigint COMMENT '父级ID 自我參照'")
    private Long parentId;

    @Column(columnDefinition = " varchar(255) COMMENT '連結地址'")
    private String href;

    @Column(columnDefinition = " varchar(255) COMMENT '圖像'")
    private String iconCls;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT '排序'")
    private Integer sortNum = 0;

    @Column(columnDefinition = " varchar(255) COMMENT '備註'")
    private String remark;

    @OneToMany(mappedBy = "sysMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SysRoleMenu> sysRoleMenus = new HashSet<>(); // 該菜單所屬的角色集合
}
