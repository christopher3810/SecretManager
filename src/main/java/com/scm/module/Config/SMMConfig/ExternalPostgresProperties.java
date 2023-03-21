package com.scm.module.Config.SMMConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "postrgres")
@Component
public class ExternalPostgresProperties {
    private String url;
    private String username;
    private String password;
}
