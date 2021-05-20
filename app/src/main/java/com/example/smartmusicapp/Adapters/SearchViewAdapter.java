package com.example.smartmusicapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmusicapp.Albums.AllSongs;
import com.example.smartmusicapp.Fragments.FragmentCommunication;
import com.example.smartmusicapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.ViewHolder> {

    public Context context;
    public List<AllSongs> searchedList;
    private FragmentCommunication mCommunicator;

    public SearchViewAdapter(Context context, List<AllSongs> searchedList,FragmentCommunication communication) {
        this.context = context;
        this.searchedList = searchedList;
        this.mCommunicator=communication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searched_row,parent,false);
        return new ViewHolder(view,mCommunicator);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllSongs song = searchedList.get(position);
        Glide.with(context)
                .load(song.getImage())
                .into(holder.searchImage);
        holder.searchName.setText(song.getName());

    }

    @Override
    public int getItemCount() {
        return searchedList.size();
    }

    public void filteredList(List<AllSongs> filteredList)
    {
        searchedList = filteredList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView searchImage;
        public TextView searchName;
        FragmentCommunication fragmentCommunication;

        public ViewHolder(@NonNull View itemView,FragmentCommunication communicator) {
            super(itemView);

            searchImage = itemView.findViewById(R.id.search_image);
            searchName = itemView.findViewById(R.id.search_text);
            fragmentCommunication=communicator;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCommunicator.respond(searchedList.get(getAdapterPosition()));
        }
    }

}
