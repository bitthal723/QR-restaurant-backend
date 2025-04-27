package com.qrfoodorder.restaurant;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class RestaurantApplication {
	public static Firestore firestore;

	public static void main(String[] args) throws IOException {
//		FileInputStream serviceAccount =
//				new FileInputStream("src/main/resources/serviceAccountKey.json");
//
//		FirebaseOptions options = FirebaseOptions.builder()
//				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//				.build();
//
//		FirebaseApp.initializeApp(options);


		try {
			FirestoreOptions firestoreOptions =
					FirestoreOptions.getDefaultInstance().toBuilder().build();
			firestore = firestoreOptions.getService();
			System.out.println("Firestore initialized successfully!");
		} catch (Exception e) {
			System.out.println("Failed to initialize Firestore!");
			e.printStackTrace();
		}



		SpringApplication.run(RestaurantApplication.class, args);
	}
}
