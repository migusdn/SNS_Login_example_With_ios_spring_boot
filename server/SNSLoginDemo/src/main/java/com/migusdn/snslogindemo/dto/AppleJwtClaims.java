package com.migusdn.snslogindemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : SNSLoginDemo
 * @name : AppleClaimsDto
 * @date : 2023/08/23 3:39 AM
 * @modifyed : $
 **/
@Getter
@Setter
@ToString
public class AppleJwtClaims {
    private String iss;
    private String aud;
    private long exp;
    private long iat;
    private String sub;
    private String at_hash;
    private String email;
    private String email_verified;
    private long auth_time;
    private boolean nonce_supported;

    // ... getters, setters
}
