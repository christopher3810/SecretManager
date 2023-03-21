package com.scm.module.Config.SMMConfig;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "vault")
@Component 
public class ExternalVaultProperties{
    @Getter
    private String url;
}
