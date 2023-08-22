package com.migusdn.snslogindemo.dto.request;


public record AppleAuthRequest(
        String firstName,
        String lastName,
        String authorizationCode
) {
    public String getUserName() {
        return firstName + lastName;
    }
}
