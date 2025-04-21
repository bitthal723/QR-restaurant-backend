package com.qrfoodorder.restaurant;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class RestaurantApplication {

	public static void main(String[] args) throws IOException {
		FileInputStream serviceAccount =
				new FileInputStream("src/main/resources/serviceAccountKey.json");

		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp.initializeApp(options);
		SpringApplication.run(RestaurantApplication.class, args);
	}

}
