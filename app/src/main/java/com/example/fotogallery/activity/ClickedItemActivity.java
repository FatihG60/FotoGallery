package com.example.fotogallery.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fotogallery.R;
import com.example.fotogallery.adapter.RecyclerGridViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClickedItemActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    ImageView imageView;
    TextView userName;
    TextView title;
    Button buttonLike;

    String likes;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        imageView = findViewById(R.id.imageViewClickedItem);
        title = findViewById(R.id.textViewClickedItem);
        userName = findViewById(R.id.userNameClickedItem);
        buttonLike = findViewById(R.id.buttonLike);

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            String imageUrl = intent.getStringExtra("image");
            String titles = intent.getStringExtra("title");
            String userNames = intent.getStringExtra("userName");
            likes = intent.getStringExtra("like");
            id = intent.getStringExtra("id");
            Picasso.get().load(imageUrl).into(imageView);
            title.setText(titles);
            userName.setText(userNames);
            buttonLike.setText(likes);
        }

        likeControlMethod();

    }



    public void like(View view) {
        buttonLike.setEnabled(false);

        DocumentReference documentReference = firebaseFirestore.collection("Posts").document(id);
        documentReference.update("likes", FieldValue.increment(1));
        documentReference.update("userLikes", FieldValue.arrayUnion(firebaseUser.getEmail()));

        buttonLike.setText(documentReference.get().getResult().get("likes").toString());

    }


    public void likeControlMethod() {

        DocumentReference documentReference = firebaseFirestore.collection("Posts").document(id);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<String> names = (ArrayList<String>) documentSnapshot.get("userLikes");

                    if (names != null) {

                        for (String name : names) {
                            if (name.equals(firebaseUser.getEmail())) {
                                buttonLike.setEnabled(false);
                            }
                        }
                    }

                }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ClickedItemActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}