package com.scm.module.Config;

import com.scm.module.Connection.VaultWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${vault.url}")
    private String vaultBaseUrl;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public VaultWebClient vaultWebClient(WebClient.Builder webClientBuilder) {
        return new VaultWebClient(webClientBuilder, vaultBaseUrl);
    }
}
