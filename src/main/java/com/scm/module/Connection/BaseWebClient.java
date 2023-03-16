package com.scm.module.Connection;

import org.springframework.web.reactive.function.client.WebClient;
public abstract class BaseWebClient {

    protected WebClient webClient;

    public BaseWebClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }
}

