package com.example.demoapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "test")
@Data
public class TestConfig {

    private String testName;

    private String testUrl;
}
