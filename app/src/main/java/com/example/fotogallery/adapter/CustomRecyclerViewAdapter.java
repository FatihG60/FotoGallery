package com.example.fotogallery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fotogallery.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.PostInfo> {


    private ArrayList<String> postTitles;
    private ArrayList<String> postImages;
    private ArrayList<Number> postLikes;

    public CustomRecyclerViewAdapter(ArrayList<String> postTitles, ArrayList<String> postImages, ArrayList<Number> postLikes) {
        this.postTitles = postTitles;
        this.postImages = postImages;
        this.postLikes = postLikes;
    }

    @NonNull
    @Override
    public PostInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_row, parent, false);
        return new PostInfo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostInfo holder, int position) {
        holder.textView.setText(postTitles.get(position));
        holder.like.setText(postLikes.get(position).toString());
        Picasso.get().load(postImages.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return postTitles.size();
    }

    public class PostInfo extends  RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private TextView like;

        public PostInfo(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.postimageView);
            textView = itemView.findViewById(R.id.posttextView);
            like = itemView.findViewById(R.id.postLikes);
        }
    }
}
