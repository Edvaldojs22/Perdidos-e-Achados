package com.edvaldo.perdidos_achados.security.config;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void inicializarFirebase() throws IOException {
        InputStream serviceAccount;

        // Verifica se está rodando no Render com variável de ambiente
        String firebaseEnv = System.getenv("FIREBASE_CONFIG");

        if (firebaseEnv != null && !firebaseEnv.isBlank()) {
            // Converte os \\n em \n reais
            String jsonComQuebras = firebaseEnv.replace("\\n", "\n");
            serviceAccount = new ByteArrayInputStream(jsonComQuebras.getBytes(StandardCharsets.UTF_8));
        } else {
            // Usa o arquivo local para desenvolvimento
            serviceAccount = new FileInputStream("src/main/resources/firebase/firebase-credencias.json");
        }

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setStorageBucket("perdidos-e-achados-2d103.appspot.com")
            .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            System.out.println("✅ Firebase inicializado com sucesso!");
        }
    }
}
