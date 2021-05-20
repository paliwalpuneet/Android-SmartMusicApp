package com.example.smartmusicapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmusicapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class WelcomeImageAdapter extends RecyclerView.Adapter<WelcomeImageAdapter.ViewHolder> {

    private Context context;
    private List<Integer> welcomeImageList = new ArrayList<>();

    public WelcomeImageAdapter(Context context, List<Integer> welcomeImageList) {
        this.context = context;
        this.welcomeImageList = welcomeImageList;
    }

    @NonNull
    @Override
    public WelcomeImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welcome_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeImageAdapter.ViewHolder holder, int position) {

        int id=welcomeImageList.get(position);
        holder.imageView.setImageResource(id);

    }

    @Override
    public int getItemCount() {
        return welcomeImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.welcomeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
