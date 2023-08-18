package com.migusdn.snslogindemo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : SNSLoginDemo
 * @name : Role
 * @date : 2023/08/17 10:10 PM
 * @modifyed : $
 **/
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
