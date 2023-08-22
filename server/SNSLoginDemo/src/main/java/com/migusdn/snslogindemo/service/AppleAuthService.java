package com.migusdn.snslogindemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migusdn.snslogindemo.client.AppleAuthClient;
import com.migusdn.snslogindemo.config.security.JwtValidator;
import com.migusdn.snslogindemo.dto.AppleJwtClaims;
import com.migusdn.snslogindemo.dto.request.AppleAuthRequest;
import com.migusdn.snslogindemo.dto.request.AppleTokenRequest;
import com.migusdn.snslogindemo.dto.response.AppleTokenResponse;
import com.migusdn.snslogindemo.util.AppleAuthUtility;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class AppleAuthService {
    private static final Logger logger = LoggerFactory.getLogger(AppleAuthService.class);
    private final AppleAuthClient appleAuthClient;
    private final AppleAuthUtility appleAuthUtility;
    private final JwtValidator jwtValidator;
    private final ObjectMapper objectMapper;
    @Value("${APPLE_CLIENT_ID:}")
    private String client_id;

    public void loadUser(AppleAuthRequest appleAuthRequest) throws AuthenticationException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, URISyntaxException {
        String name = appleAuthRequest.getUserName();
        System.out.println("name = " + name);
        AppleTokenResponse appleTokenResponse = getAppleToken(appleAuthRequest.authorizationCode());
        System.out.println("appleTokenResponse = " + appleTokenResponse);
        AppleJwtClaims appleJwtClaimsDTO = getAppleJwtClaimsDTO(appleTokenResponse.getIdToken());
        System.out.println("appleJwtClaimsDTO = " + appleJwtClaimsDTO);


    }

    private AppleJwtClaims getAppleJwtClaimsDTO(String idToken) throws IOException, AuthenticationException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException {

        Map<String, String> headers = jwtValidator.parseHeaders(idToken);
        PublicKey publicKey = appleAuthUtility.generatePublicKey(headers, appleAuthClient.getPublicKeys());
        logger.info("test" + jwtValidator.getTokenClaims(idToken, publicKey).toString());
        Claims claims = jwtValidator.getTokenClaims(idToken, publicKey);
        return jwtValidator.parseJwtToAppleClaimsDto(claims);
        //{iss=https://appleid.apple.com, aud=com.migusdn.snsLoginExample, exp=1692813910, iat=1692727510, sub=000608.689178f115cf417c9a11496fe31ca3dd.0550, at_hash=lQv1foyco4H2Xh2vMIqfBg, email=migusdn@gmail.com, email_verified=true, auth_time=1692727495, nonce_supported=true}
    }

    private AppleTokenResponse getAppleToken(String authorizationCode) throws IOException, AuthenticationException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException {
        AppleTokenRequest appleTokenRequest = AppleTokenRequest.builder().client_secret(appleAuthUtility.generateSecretKey()).client_id(client_id).code(authorizationCode).grant_type("authorization_code").build();
        log.info(appleTokenRequest.getFormData().toString());
        return appleAuthClient.getAppleToken(appleTokenRequest);

    }
}
