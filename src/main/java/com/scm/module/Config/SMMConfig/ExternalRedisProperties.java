package com.scm.module.Config.SMMConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "redis")
@Component
public class ExternalRedisProperties {
    private String host;
    private int port;
}
