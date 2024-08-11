package com.example.demoservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@Entity(name = "SysMenu")
@Table(name = "sys_menu")
public class SysMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = " varchar(255) COMMENT '菜單名稱'")
    private String menuName;

    @Column(columnDefinition = " varchar(255) COMMENT '平台名稱'")
    private String platformName;

    @Column(columnDefinition = " bigint COMMENT '父级ID 自我參照'")
    private Long parentId;

    @Column(columnDefinition = " varchar(255) COMMENT '連接地址'")
    private String href;

    @Column(columnDefinition = " varchar(255) COMMENT '圖像'")
    private String iconCls;

    @Column(columnDefinition = " int DEFAULT 0 COMMENT '排序'")
    private Integer sortNum ;

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
