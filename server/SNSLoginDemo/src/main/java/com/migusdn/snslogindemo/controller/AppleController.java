package com.migusdn.snslogindemo.controller;

import com.migusdn.snslogindemo.dto.request.AppleAuthRequest;
import com.migusdn.snslogindemo.dto.response.AppleEventResponse;
import com.migusdn.snslogindemo.service.AppleAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AppleController {
    private static final Logger logger = LoggerFactory.getLogger(AppleController.class);
    private final AppleAuthService appleAuthService;

    @PostMapping("/apple")
    public ResponseEntity appleCallback(
            @RequestBody @Validated AppleAuthRequest appleAuthRequest)
            throws AuthenticationException, NoSuchAlgorithmException, InvalidKeySpecException,
            IOException, URISyntaxException {
        appleAuthService.loadUser(appleAuthRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/apple/event")
    public void loggingAppleEvent(@RequestBody AppleEventResponse appleEventResponse) {
        logger.trace("[Apple Event Log] PAYLOAD : " + appleEventResponse.payload());
    }

}
