package com.example.otpverification;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class StarFragment extends Fragment {

    private FirebaseFirestore db;

    public StarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_star, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Retrieve data from Firestore
        retrieveData();
    }

    private void retrieveData() {
        db.collection("UserData")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve data from the document
                            String height = document.getString("Height");
                            String religion = document.getString("Religion");
                            String community = document.getString("Community");
                            String smoke = document.getString("Smoke");
                            String status = document.getString("Status");
                            String drinking = document.getString("Drinking");
                            String language = document.getString("Language");

                            // Set data to the TextViews
                            TextView heightTextView = getView().findViewById(R.id.heightTextView);
                            heightTextView.setText("Height: " + height);

                            TextView religionTextView = getView().findViewById(R.id.religionTextView);
                            religionTextView.setText("Religion: " + religion);

                            TextView communityTextView = getView().findViewById(R.id.communityTextView);
                            communityTextView.setText("Community: " + community);

                            TextView smokeTextView = getView().findViewById(R.id.smokeTextView);
                            smokeTextView.setText("Smoke: " + smoke);

                            TextView statusTextView = getView().findViewById(R.id.statusTextView);
                            statusTextView.setText("Status: " + status);

                            TextView drinkingTextView = getView().findViewById(R.id.drinkingTextView);
                            drinkingTextView.setText("Drinking: " + drinking);

                            TextView languageTextView = getView().findViewById(R.id.languageTextView);
                            languageTextView.setText("Language: " + language);
                        }

                        // Retrieve image URLs after retrieving data for all users
                        retrieveImageUrls();
                    } else {
                        Toast.makeText(getContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void retrieveImageUrls() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                // Iterate through the dataSnapshot to retrieve image URLs
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String imageUrl = snapshot.getValue(String.class);
                    // Load the image into the appropriate ImageView using Glide
                    loadImage(imageUrl, i);
                    i++;
                    // Stop after loading 3 images
                    if (i > 3) {
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(getContext(), "Failed to retrieve image URLs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadImage(String imageUrl, int imageViewNumber) {
        // Get the appropriate ImageView based on the number
        ImageView imageView;
        switch (imageViewNumber) {
            case 1:
                imageView = getView().findViewById(R.id.imageView1);
                break;
            case 2:
                imageView = getView().findViewById(R.id.imageView2);
                break;
            case 3:
                imageView = getView().findViewById(R.id.imageView3);
                break;
            default:
                return;
        }
        // Load image into the ImageView using Glide
        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.placeholder)) // Placeholder image
                .into(imageView);
    }
}