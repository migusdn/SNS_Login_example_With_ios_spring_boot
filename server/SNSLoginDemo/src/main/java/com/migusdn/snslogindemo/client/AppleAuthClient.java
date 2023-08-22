package com.migusdn.snslogindemo.client;


import com.migusdn.snslogindemo.dto.key.OidcPublicKeyResponse;
import com.migusdn.snslogindemo.dto.request.AppleTokenRequest;
import com.migusdn.snslogindemo.dto.response.AppleTokenResponse;
import com.migusdn.snslogindemo.properties.AppleProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@RequiredArgsConstructor
public class AppleAuthClient implements AuthClient {
    private final WebClient.Builder webClientBuilder;
    private final AppleProperties properties;

    public OidcPublicKeyResponse getPublicKeys() {
        return webClientBuilder
                .baseUrl(properties.getPublicKeyUrl())
                .build()
                .get()
                .retrieve()
                .bodyToMono(OidcPublicKeyResponse.class)
                .block();
    }

    public AppleTokenResponse getAppleToken(AppleTokenRequest appleTokenRequest) {
        return webClientBuilder
                .baseUrl(properties.getTokenUrl())
                .build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(appleTokenRequest.getFormData()))
                .retrieve()
                .bodyToMono(AppleTokenResponse.class)
                .block();
    }
}
