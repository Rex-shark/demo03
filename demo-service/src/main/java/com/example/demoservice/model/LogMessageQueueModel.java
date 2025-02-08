package com.example.demoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogMessageQueueModel implements Serializable {
    private String account;
    private String ipAddress;
    private String jwt;
    private Date loginTime;
    private Integer type;
}
