package com.edvaldo.perdidos_achados.security.config;

import java.io.FileInputStream;
import java.io.IOException;


import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {
    
    @PostConstruct
    public void inicializarFirebase() throws IOException{
        FileInputStream serviceAccount = new FileInputStream("firebase/firebase-credencias.json");
     
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setStorageBucket("perdidos-e-achados-2d103.firebasestorage.app") 
            .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase inicializado com sucesso!");
        }
    }
}
