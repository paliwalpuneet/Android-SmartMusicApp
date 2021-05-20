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
import com.example.smartmusicapp.Albums.AllSongs;
import com.example.smartmusicapp.Fragments.FragmentCommunication;
import com.example.smartmusicapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecentItemAdapter extends RecyclerView.Adapter<RecentItemAdapter.ViewHolder> {

    List<AllSongs> recentItemList = new ArrayList<>();
    Context context;
    private FragmentCommunication mCommunicator;

    public RecentItemAdapter(Context context , List<AllSongs> recentItemList,FragmentCommunication communication) {
        this.recentItemList = recentItemList;
        this.context = context;
        this.mCommunicator=communication;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_item_view,parent,false);
        return new ViewHolder(view,mCommunicator);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AllSongs song = recentItemList.get(position);
        Glide.with(context)
                .load(song.getImage())
                .into(holder.recentItemImage);
        holder.recentItemName.setText(song.getName());
    }

    @Override
    public int getItemCount() {
        return this.recentItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recentItemImage;
        TextView recentItemName;
        FragmentCommunication fragmentCommunication;

        public ViewHolder(@NonNull View itemView,FragmentCommunication communicator) {
            super(itemView);
            recentItemImage = itemView.findViewById(R.id.recentItemPic);
            recentItemName = itemView.findViewById(R.id.recentItemName);
            fragmentCommunication=communicator;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCommunicator.respond(recentItemList.get(getAdapterPosition()));

        }
    }
}
