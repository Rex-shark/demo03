package com.example.demoservice.entity;

import com.example.demoservice.entity.base.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_base")
@Data
public class UserBase extends AbstractEntity {

    @NotBlank(message = "uuid is mandatory")
    @Size(min = 36, max = 36, message = "uuid size is 36")
    @Column(updatable = false, nullable = false, length = 36, unique = true , columnDefinition = "VARCHAR(36) COMMENT 'uuid'")
    private String uuid;

    @Column(updatable = false, nullable = false, length = 100, unique = true ,columnDefinition = "varchar(100) COMMENT '登入帳號'")
    private String account;

    @JsonIgnore
    @Column(nullable = false, length = 100, columnDefinition = "VARCHAR(100) COMMENT '登入密碼'")
    private String password;

    @Column(columnDefinition = " varchar(255) COMMENT '備註'")
    private String remark;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private LocalDateTime lastLogin;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private LocalDateTime lastLogout;

    //注意FetchType
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "sys_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private List<SysRole> roles = new ArrayList<>();

    @PrePersist
    private void initPrePersist() {
        super.init();
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();  // 生成 UUID
        }
    }
}
