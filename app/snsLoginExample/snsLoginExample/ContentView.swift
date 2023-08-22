//
//  ContentView.swift
//  snsLoginExample
//
//  Created by HyunwooPark on 2023/08/19.
//

import SwiftUI
import AuthenticationServices

struct ContentView: View {
    var body: some View {
        VStack {
            SignInWithAppleView()
        }
        .frame(height:UIScreen.main.bounds.height)
        .background(Color.white)
    
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
