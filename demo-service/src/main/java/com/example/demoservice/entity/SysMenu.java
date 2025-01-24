package com.example.demoservice.entity;

import com.example.demoservice.entity.base.AbstractEntity;
import com.example.demoservice.request.api.SysMenuRequest;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "SysMenu")
@Table(name = "sys_menu")
@NoArgsConstructor
public class SysMenu extends AbstractEntity {
  

    public SysMenu(SysMenuRequest request){
        this.nid = request.getNid();
        this.menuName = request.getMenuName();
        this.href = request.getHref();
        this.iconCls = request.getIconCls();
        this.parentId = request.getParentId();
        this.platformName = request.getPlatformName();
        this.sortNum = request.getSortNum();
        this.remark = request.getRemark();
    }

    @PrePersist
    private void initPrePersist() {
        super.init();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore(false)
    private Long id;

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

    //@JsonManagedReference
    @JsonBackReference
    @OneToMany(mappedBy = "sysMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SysRoleMenu> sysRoleMenus = new ArrayList<>(); // 該菜單所屬的角色集合
}
