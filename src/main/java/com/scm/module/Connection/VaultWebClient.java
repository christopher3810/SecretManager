package com.scm.module.Connection;

@Component
public class VaultWebClient extends BaseWebClient {

    private final String vaultUrl;
    private final String secretPath;

    public VaultWebClient(WebClient.Builder webClientBuilder,
        @Value("${vault.url}") String vaultUrl,
        @Value("${vault.secretPath}") String secretPath) {
        super(webClientBuilder);
        this.vaultUrl = vaultUrl;
        this.secretPath = secretPath;
    }

    public Mono<Map<String, Object>> retrieveEncryptedUserData(String email) {
        String uri = vaultUrl + "/v1/" + secretPath + "/users/" + email;
        return this.get(uri, Map.class);
    }

    public Mono<Void> putEncryptedUserData(String email, Map<String, Object> encryptedUserData) {
        String uri = vaultUrl + "/v1/" + secretPath + "/users/" + email;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return this.put(uri, headers, BodyInserters.fromValue(encryptedUserData), Void.class);
    }
}