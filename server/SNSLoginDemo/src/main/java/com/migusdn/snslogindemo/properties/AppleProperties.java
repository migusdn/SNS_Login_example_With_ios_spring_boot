package com.migusdn.snslogindemo.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
public class AppleProperties {
    @Value("${oauth2.apple.auth.public-key-url}")
    private String publicKeyUrl;
    @Value("https://appleid.apple.com/auth/token")
    private String tokenUrl;
}
