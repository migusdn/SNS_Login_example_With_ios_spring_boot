package com.migusdn.snslogindemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migusdn.snslogindemo.dto.key.OidcPublicKey;
import com.migusdn.snslogindemo.dto.key.OidcPublicKeyResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppleAuthUtility {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Value("${oauth2.apple.team-id}")
    private String teamId;
    @Value("${APPLE_CLIENT_ID}")
    private String clientId;
    @Value("${APPLE_KEY_PATH}")
    private String keyPath;
    @Value("${oauth2.apple.key.id}")
    private String keyId;

    public PublicKey generatePublicKey(Map<String, String> tokenHeaders,
                                       OidcPublicKeyResponse applePublicKeys)
            throws AuthenticationException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException, IOException {
        OidcPublicKey publicKey = getOidcPublicKey(tokenHeaders, applePublicKeys);

        return getPublicKey(publicKey);
    }

    public String generateSecretKey() throws AuthenticationException {

        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        try {
            return Jwts.builder()
                    .setHeaderParam("kid", keyId)
                    .setHeaderParam("alg", "ES256")
                    .setIssuer(teamId)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expirationDate)
                    .setAudience("https://appleid.apple.com")
                    .setSubject(clientId)
                    .signWith(this.getPrivateKey(), SignatureAlgorithm.ES256)
                    .compact();

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey getPublicKey(OidcPublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException, IOException {
        byte[] nBytes = Base64.getUrlDecoder().decode(publicKey.n());
        byte[] eBytes = Base64.getUrlDecoder().decode(publicKey.e());

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, nBytes),
                new BigInteger(1, eBytes));

//        getPrivateKey();
        KeyFactory keyFactory = KeyFactory.getInstance(publicKey.kty());
        return keyFactory.generatePublic(publicKeySpec);
    }

    //get private key from assets p8 file
    private PrivateKey getPrivateKey() throws URISyntaxException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(keyPath)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            log.info("privateKey: " + privateKey);
            KeyFactory kf = KeyFactory.getInstance("EC");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Java did not support the algorithm:" + "EC", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid key format");
        }
    }

    private OidcPublicKey getOidcPublicKey(Map<String, String> tokenHeaders,
                                           OidcPublicKeyResponse applePublicKeys) throws AuthenticationException {
        return applePublicKeys.getMatchedKey(tokenHeaders.get("kid"),
                tokenHeaders.get("alg"));
    }

}
