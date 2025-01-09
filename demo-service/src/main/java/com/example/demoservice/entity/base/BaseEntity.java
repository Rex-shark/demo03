package com.example.demoservice.entity.base;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 * 使用@MappedSuperclass可以將常用的欄位抽出，讓其他Entity繼承
 * 可是這樣其他實體便無法get,set BaseEntity的欄位
 * 感覺不太好用，先保留當範例
 */
@MappedSuperclass
public class BaseEntity {


    @Column(nullable = false)
    private Integer status;  // 狀態

    @Column(nullable = false)
    private Long createdUserId;

    @Column()
    private Long updateUserId;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;

    protected void init() {
        if(this.status == null) {
            this.status = 1 ;
        }
        if(this.createdUserId == null) {
            this.createdUserId = 1L ;
        }
    }
}
