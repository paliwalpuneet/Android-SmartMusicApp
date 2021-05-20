package com.example.smartmusicapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.smartmusicapp.Albums.AllSongs;

import com.example.smartmusicapp.Fragments.FragmentCommunication;
import com.example.smartmusicapp.Fragments.FragmentHome;
import com.example.smartmusicapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.ViewHolder> {

    List<AllSongs> allSongsList = new ArrayList<>();
    Context context;
    private FragmentCommunication mCommunicator;


    public AllSongsAdapter(Context context , List<AllSongs> allSongsList,FragmentCommunication communication) {
        this.allSongsList = allSongsList;
        Collections.shuffle(this.allSongsList);
        this.context = context;
        this.mCommunicator=communication;
    }




    @NonNull
    @Override
    public AllSongsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_songs_view,parent,false);
        return new AllSongsAdapter.ViewHolder(view,mCommunicator);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AllSongs song = allSongsList.get(position);
        Glide.with(context)
                .load(song.getImage())
                .into(holder.allSongImage);
        holder.allSongName.setText(song.getName());

    }

    @Override
    public int getItemCount() {
        return this.allSongsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView allSongImage;
        TextView allSongName;
        FragmentCommunication fragmentCommunication;

        public ViewHolder(@NonNull View itemView,FragmentCommunication communicator) {
            super(itemView);
            allSongImage = itemView.findViewById(R.id.all_song_image);
            allSongName = itemView.findViewById(R.id.all_song_name);
            fragmentCommunication=communicator;
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {

            mCommunicator.respond(allSongsList.get(getAdapterPosition()));




        }
    }
}

