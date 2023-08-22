package com.migusdn.snslogindemo.client;


import com.migusdn.snslogindemo.dto.key.OidcPublicKeyResponse;

public interface AuthClient {
    OidcPublicKeyResponse getPublicKeys();
}
