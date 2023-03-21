package com.scm.module.Config.SMMConfig;

import com.scm.module.Connection.VaultWebClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties({ExternalVaultProperties.class, ExternalRedisProperties.class, ExternalPostgresProperties.class})
public class SMMConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public VaultWebClient vaultWebClient(WebClient.Builder webClientBuilder, ExternalVaultProperties vaultProperties) {
        return new VaultWebClient(webClientBuilder, vaultProperties.getUrl());
    }

}
