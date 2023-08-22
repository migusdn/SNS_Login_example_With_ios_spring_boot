//
//  SignInWithAppleView.swift
//  snsLoginExample
//
//  Created by HyunwooPark on 2023/08/19.
//

import AuthenticationServices
import SwiftUI

struct SignInWithAppleView: View {
    var body: some View {
        SignInWithAppleButton(
            onRequest: { request in
                request.requestedScopes = [.fullName, .email]
            },
            onCompletion: { result in
                switch result {
                case let .success(authResult):
                    switch authResult.credential {
                    case let appleIDCredential as ASAuthorizationAppleIDCredential:
                        self.sendAuthenticationToServer(appleIDCredential: appleIDCredential)
                    default:
                        break
                    }
                case let .failure(error):
                    print("Sign in with Apple failed: \(error)")
                }
            }
        )
        .frame(width: 200, height: 50)
        .signInWithAppleButtonStyle(.black)
    }

    func sendAuthenticationToServer(appleIDCredential: ASAuthorizationAppleIDCredential) {
        // You can create an account in your system.
        let userIdentifier = appleIDCredential.user
        let fullName = appleIDCredential.fullName
        let email = appleIDCredential.email

        if let authorizationCode = appleIDCredential.authorizationCode,
           let identityToken = appleIDCredential.identityToken,
           let authCodeString = String(data: authorizationCode, encoding: .utf8),
           let identifyTokenString = String(data: identityToken, encoding: .utf8)

        {
            print("authorizationCode: \(authorizationCode)")
            print("identityToken: \(identityToken)")
            print("authCodeString: \(authCodeString)")
            print("identifyTokenString: \(identifyTokenString)")
            let networkManager = NetworkManager()
            networkManager.sendSignInRequest(authCodeString: authCodeString, appleIDCredential: appleIDCredential)
//            var req = SignInRequest(idToken: identifyTokenString, authorizationCode: authCode, user: SignInRequest.User(email: "test", name: "test", ))
        }

        print("useridentifier: \(userIdentifier)")
        print("fullName: \(fullName)")
        print("email: \(email)")
        // 서버로 인증 정보 전송 코드 작성
        // URLSession 등을 사용하여 서버로 POST 요청을 보낼 수 있습니다.
    }
}

struct SignInRequest: Codable {
    let authorizationCode: String
    let firstName: String
    let lastName: String
}
