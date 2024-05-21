package com.example.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class S2 extends AppCompatActivity {
    Spinner spinner;
    String[] height = {"- -", "4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.7", "4.8", "4.9", "4.10", "4.11", "5.0", "5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10", "5.11", "6.0", "6.1", "6.2", "6.3", "6.4", "6.5"};
    Spinner spinner1;
    String[] Religion = {"- -", "Hindu", "Muslim", "Christian", "Spiritual", "Atheist", "Buddhist", "Jain", "Shinto", "Islam", "Sikh", "Judaism", "Others"};
    Spinner spinner2;
    String[] Community = {"- -", "Brahmin", "Thakur", "Rajput", "Kappu Naidu", "Kamma", "Vaishya", "Reddy", "Chowdary", "Komati", "Nair", "Others"};
    Spinner spinner3;
    String[] Smoke = {"- -", "Yes", "No", "Planning to Quit"};

    Spinner spinner4;
    String[] Status = {"- -", "Single", "Married", "Unmarried", "Committed", "Divorced", "Divorced with kids", "Widowed", "Others"};
    Spinner spinner5;

    String[] Drinking = {"- -", "Regular", "Socially", "Planning to Quit", "Occasionally", "Teetotaler"};

    Spinner spinner6;

    String[] Language = {"- -", "English", "Telugu", "Hindi", "Tamil", "Kannada", "Malayalam", "Urdu", "Spanish"};

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);

        db = FirebaseFirestore.getInstance();

        spinner = findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirestore(
                        spinner.getSelectedItem().toString(),
                        spinner1.getSelectedItem().toString(),
                        spinner2.getSelectedItem().toString(),
                        spinner3.getSelectedItem().toString(),
                        spinner4.getSelectedItem().toString(),
                        spinner5.getSelectedItem().toString(),
                        spinner6.getSelectedItem().toString()
                );
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(S2.this, R.layout.item_file, height);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(S2.this, R.layout.item_file, Religion);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(S2.this, R.layout.item_file, Community);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(S2.this, R.layout.item_file, Smoke);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(S2.this, R.layout.item_file, Status);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(S2.this, R.layout.item_file, Drinking);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(S2.this, R.layout.item_file, Language);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);
    }

    private void saveToFirestore(String height, String religion, String community, String smoke, String status, String drinking, String language) {
        // Get the current user's UID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Create a map with user details
        Map<String, Object> data = new HashMap<>();
        data.put("Height", height);
        data.put("Religion", religion);
        data.put("Community", community);
        data.put("Smoke", smoke);
        data.put("Status", status);
        data.put("Drinking", drinking);
        data.put("Language", language);

        // Save the data to Firestore with the user's UID as the document ID
        db.collection("UserData")
                .document(uid) // Use the user's UID as the document ID
                .set(data) // Set data for the document
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(S2.this, "Data saved to Firestore", Toast.LENGTH_SHORT).show();
                            moveToNextActivity(); // Move to the next activity after data is saved
                        } else {
                            Toast.makeText(S2.this, "Failed to save data to Firestore: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Firestore", "Error saving data to Firestore", task.getException());
                        }
                    }
                });
    }

    // Method to move to the next activity
    private void moveToNextActivity() {
        Intent intent = new Intent(S2.this, MainActivity.class);
        startActivity(intent);
    }
}
