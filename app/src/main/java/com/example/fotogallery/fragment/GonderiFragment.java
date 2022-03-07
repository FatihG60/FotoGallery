package com.example.fotogallery.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fotogallery.adapter.CustomRecyclerViewAdapter;
import com.example.fotogallery.R;
import com.example.fotogallery.activity.UploadActivity;
import com.example.fotogallery.model.PostData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;


public class GonderiFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    PostData postData;
    CustomRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public static GonderiFragment getInstance() {
        return new GonderiFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postData = new PostData();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        getData();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gonderi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new CustomRecyclerViewAdapter(postData.getPostTitles(), postData.getPostImages(), postData.getPostLikes());
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), UploadActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getData() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        CollectionReference collectionReference = firebaseFirestore.collection("Posts");
        collectionReference.whereEqualTo("eMail", firebaseUser.getEmail()).orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (value != null) {

                    for (DocumentSnapshot snapshot : value.getDocuments()) {

                        Map<String, Object> data = snapshot.getData();
                        String title = (String) data.get("title");
                        String downloadUrl = (String) data.get("downloadUrl");
                        Number like = (Number) data.get("likes");

                        postData.getPostTitles().add(title);
                        postData.getPostImages().add(downloadUrl);
                        postData.getPostLikes().add(like);

                        adapter.notifyDataSetChanged();

                    }

                }

            }
        });

    }
}