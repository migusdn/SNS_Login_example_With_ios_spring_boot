package com.migusdn.snslogindemo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : SNSLoginDemo
 * @name : AppleTokenRequest
 * @date : 2023/08/22 3:28 PM
 * @modifyed : $
 **/
@Getter
@Builder
@ToString
public class AppleTokenRequest {
    private final String client_id;
    private final String client_secret;
    private final String code;
    private final String grant_type;
    private final String redirect_uri;

    public MultiValueMap<String, String> getFormData() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        if (client_id != null)
            formData.add("client_id", this.client_id);
        if (client_secret != null)
            formData.add("client_secret", client_secret);
        if (code != null)
            formData.add("code", code);
        if (grant_type != null)
            formData.add("grant_type", grant_type);
        if (redirect_uri != null)
            formData.add("redirect_uri", redirect_uri);
        return formData;
    }
}
