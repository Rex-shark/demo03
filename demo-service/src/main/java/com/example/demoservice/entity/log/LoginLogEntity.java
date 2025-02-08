package com.example.demoservice.entity.log;

import com.example.demoservice.entity.base.AbstractEntity;
import com.example.demoservice.model.LogMessageQueueModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "login_log")
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class LoginLogEntity extends AbstractEntity {

    public LoginLogEntity(LogMessageQueueModel model){
        this.account = model.getAccount();
        this.jwt = model.getJwt();
        this.type = model.getType();
        this.LoginAt = model.getLoginTime();
    }

    @PrePersist
    private void initPrePersist() {
        super.init();
    }

    @Column(updatable = false, length = 100, unique = true ,columnDefinition = "varchar(100) COMMENT '登入帳號'")
    private String account;

    @Column(unique = false)
    private String jwt;

    private Integer type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date LoginAt;
}
