package com.example.fotogallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fotogallery.R;
import com.example.fotogallery.activity.ClickedItemActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerGridViewAdapter extends RecyclerView.Adapter<RecyclerGridViewAdapter.ImageInfo> {

    private ArrayList<String> postImages;
    private ArrayList<String> postTitles;
    private ArrayList<String> postUserNames;
    private ArrayList<Number> postLikes;
    private ArrayList<String> postIds;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecyclerGridViewAdapter(ArrayList<String> postUserNames, ArrayList<String> postTitles, ArrayList<String> postImages, ArrayList<Number> postLikes, ArrayList<String> postIds, Context context) {
        this.postUserNames = postUserNames;
        this.postTitles = postTitles;
        this.postImages = postImages;
        this.postLikes = postLikes;
        this.postIds = postIds;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ImageInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_items, parent, false);
        return new ImageInfo(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageInfo holder, int position) {
        Picasso.get().load(postImages.get(position)).into(holder.imageView);
        holder.itemClick(position);
    }

    @Override
    public int getItemCount() {
        return postImages.size();
    }

    public class ImageInfo extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImageInfo(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItem);

        }

        public void itemClick(int index) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ClickedItemActivity.class);
                    context.startActivity(intent
                            .putExtra("image", postImages.get(index))
                            .putExtra("title",postTitles.get(index))
                            .putExtra("userName",postUserNames.get(index))
                            .putExtra("like", postLikes.get(index).toString())
                            .putExtra("id", postIds.get(index))
                            );
                }
            });
        }

    }

}
