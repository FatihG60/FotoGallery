package com.example.fotogallery.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fotogallery.adapter.RecyclerGridViewAdapter;
import com.example.fotogallery.R;
import com.example.fotogallery.model.PostData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;


public class KesfetFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    PostData postData;
    RecyclerGridViewAdapter adapter;
    RecyclerView recyclerViewGrid;

    public static KesfetFragment getInstance() {
        return new KesfetFragment();
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
        return inflater.inflate(R.layout.fragment_kesfet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewGrid = view.findViewById(R.id.recyclerViewGrid);
        recyclerViewGrid.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        adapter = new RecyclerGridViewAdapter(postData.getPostUserNames(), postData.getPostTitles(), postData.getPostImages(), postData.getPostLikes(), postData.getPostIds(), requireActivity());
        recyclerViewGrid.setAdapter(adapter);

    }


    public void getData() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        CollectionReference collectionReference = firebaseFirestore.collection("Posts");
        collectionReference.whereNotEqualTo("eMail", firebaseUser.getEmail()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(requireActivity(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if (value != null) {

                    int index = 0;
                    for (DocumentSnapshot snapshot : value.getDocuments()) {

                        Map<String, Object> data = snapshot.getData();
                        String title = (String) data.get("title");
                        String downloadUrl = (String) data.get("downloadUrl");
                        String userName = (String) data.get("userName");
                        String id = snapshot.getId();
                        Number like = (Number) data.get("likes");

                        postData.getPostTitles().add(title);
                        postData.getPostImages().add(downloadUrl);
                        postData.getPostUserNames().add(userName);
                        postData.getPostLikes().add(like);
                        postData.getPostIds().add(id);

                        //adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(index);
                        index++;

                    }

                }

            }
        });

    }

}