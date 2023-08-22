package com.migusdn.snslogindemo.dto.key;

public record OidcPublicKey(
        String kid,
        String kty,
        String alg,
        String use,
        String n,
        String e) {
}
