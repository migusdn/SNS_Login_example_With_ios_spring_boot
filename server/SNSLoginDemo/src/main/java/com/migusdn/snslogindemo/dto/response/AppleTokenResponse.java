package com.migusdn.snslogindemo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : SNSLoginDemo
 * @name : AppleTokenResponse
 * @date : 2023/08/22 3:29 PM
 * @modifyed : $
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AppleTokenResponse {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("expires_in")
    int expiresIn;
    @JsonProperty("id_token")
    String idToken;
    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("token_type")
    String tokenType;

}
