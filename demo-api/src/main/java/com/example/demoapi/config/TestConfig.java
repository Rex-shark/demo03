package com.example.demoapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 不知道目前有什麼實際應用，放著當範例
 */
@Component
@ConfigurationProperties(prefix = "test")
@Data
public class TestConfig {

    private String testName;

    private String testUrl;
}
