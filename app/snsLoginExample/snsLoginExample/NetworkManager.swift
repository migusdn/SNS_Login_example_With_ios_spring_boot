//
//  File.swift
//  snsLoginExample
//
//  Created by HyunwooPark on 2023/08/23.
//

import Foundation
import SwiftUI
import Combine
import AuthenticationServices

class TrustAllCertsDelegate: NSObject, URLSessionDelegate {
    func urlSession(_ session: URLSession, didReceive challenge: URLAuthenticationChallenge, completionHandler: @escaping (URLSession.AuthChallengeDisposition, URLCredential?) -> Void) {
        completionHandler(.useCredential, URLCredential(trust: challenge.protectionSpace.serverTrust!))
    }
}

let config = URLSessionConfiguration.default
let session = URLSession(configuration: config, delegate: TrustAllCertsDelegate(), delegateQueue: nil)

class NetworkManager: ObservableObject {
    func sendSignInRequest(authCodeString: String, appleIDCredential: ASAuthorizationAppleIDCredential) {
        let url = URL(string: "https://localhost/api/auth/apple")!
        
        let requestModel = SignInRequest(
            authorizationCode: authCodeString,
            firstName: appleIDCredential.fullName?.givenName ?? "",
            lastName: appleIDCredential.fullName?.familyName ?? ""
        )
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        do {
            let requestBody = try JSONEncoder().encode(requestModel)
            request.httpBody = requestBody
        } catch {
            print("Error encoding request body: \(error)")
            return
        }
        
        // Use the custom session instead of URLSession.shared
        session.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Error sending request: \(error)")
                return
            }
            
            if let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode != 200 {
                print("Server responded with status code: \(httpResponse.statusCode)")
                return
            }
            
            // Handle response data if needed
        }.resume()
    }
}
