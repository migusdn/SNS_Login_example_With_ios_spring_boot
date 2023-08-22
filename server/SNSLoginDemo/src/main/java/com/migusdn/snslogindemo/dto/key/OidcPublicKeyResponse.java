package com.migusdn.snslogindemo.dto.key;

import javax.naming.AuthenticationException;
import java.util.List;

public record OidcPublicKeyResponse(List<OidcPublicKey> keys) {

    public OidcPublicKey getMatchedKey(String kid, String alg) throws AuthenticationException {
        return keys.stream()
                   .filter(key -> key.kid().equals(kid) && key.alg().equals(alg))
                   .findAny()
                   .orElseThrow(AuthenticationException::new);
    }
}
