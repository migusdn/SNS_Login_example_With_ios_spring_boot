package com.migusdn.snslogindemo.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migusdn.snslogindemo.dto.AppleJwtClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtValidator {
    public Map<String, String> parseHeaders(String token) throws JsonProcessingException {
        String header = token.split("\\.")[0];
        return new ObjectMapper().readValue(decodeHeader(header), Map.class);
    }

    public String decodeHeader(String token) {
        return new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
    }


    public Claims getTokenClaims(String token, PublicKey publicKey) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public AppleJwtClaims parseJwtToAppleClaimsDto(Claims claims) {
        AppleJwtClaims dto = new AppleJwtClaims();
        dto.setIss(claims.get("iss", String.class));
        dto.setAud(claims.get("aud", String.class));
        dto.setExp(claims.get("exp", Long.class));
        dto.setIat(claims.get("iat", Long.class));
        dto.setSub(claims.get("sub", String.class));
        dto.setAt_hash(claims.get("at_hash", String.class));
        dto.setEmail(claims.get("email", String.class));
        dto.setEmail_verified(claims.get("email_verified", String.class));
        dto.setAuth_time(claims.get("auth_time", Long.class));
        dto.setNonce_supported(claims.get("nonce_supported", Boolean.class));

        return dto;
    }
}
