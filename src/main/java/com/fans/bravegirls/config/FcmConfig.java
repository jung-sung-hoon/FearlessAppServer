package com.fans.bravegirls.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class FcmConfig {

    @Autowired
    ApplicationContext context;

    @Bean
    public FirebaseApp firebaseApp() throws Exception {

        InputStream is = context.getResource("classpath:firebase.json").getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(is)).build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public ListeningExecutorService firebaseAppExecutor() {
        return MoreExecutors.newDirectExecutorService();
    }
}
