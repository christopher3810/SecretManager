package com.scm.module.Connection;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class VaultWebClient extends BaseWebClient {
    public VaultWebClient(WebClient.Builder webClientBuilder, String vaultBaseUrl) {
        super(webClientBuilder, vaultBaseUrl);
    }

    public Mono<String> getSecret(String secretPath) {
        return webClient.get()
            .uri(secretPath)
            .retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> createSecret(String secretPath, String secretValue) {
        return webClient.post()
            .uri(secretPath)
            .bodyValue(secretValue)
            .retrieve()
            .bodyToMono(String.class);
    }
}
