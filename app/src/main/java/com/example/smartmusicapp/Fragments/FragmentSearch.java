package com.example.smartmusicapp.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmusicapp.Adapters.SearchViewAdapter;
import com.example.smartmusicapp.Albums.AllSongs;
import com.example.smartmusicapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentSearch extends Fragment {
    AppCompatImageView home,search,playFr,menu;
    EditText searchText;
    RecyclerView searchView;
    SearchViewAdapter searchViewAdapter;
    ArrayList<AllSongs> allSongs = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        searchText = view.findViewById(R.id.searchSong);

        searchView = view.findViewById(R.id.searchView);
        searchView.setHasFixedSize(true);
        searchView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

         allSongs =(ArrayList) requireArguments().getParcelableArrayList("All Songs");


        if(allSongs!=null) {
            searchViewAdapter = new SearchViewAdapter(getContext(), allSongs,fragmentCommunication);
            searchView.setAdapter(searchViewAdapter);
        }

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });



        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        home = getActivity().findViewById(R.id.home);
        playFr = getActivity().findViewById(R.id.play);
        search = getActivity().findViewById(R.id.search);
        menu = getActivity().findViewById(R.id.menu);

        home.setColorFilter(Color.WHITE);
        playFr.setColorFilter(Color.WHITE);
        search.setColorFilter(Color.YELLOW);
        menu.setColorFilter(Color.WHITE);
    }

    public void filter(String text)
    {
        List<AllSongs> searchedSongs = new ArrayList<>();

        for(AllSongs song : allSongs)
        {
            if(song.getName().toLowerCase().contains(text.toLowerCase()))
            {
                searchedSongs.add(song);
            }
        }
        searchViewAdapter.filteredList(searchedSongs);
    }


    FragmentCommunication fragmentCommunication = new FragmentCommunication() {
        @Override
        public void respond(AllSongs song) {

            FragmentPlay play = new FragmentPlay();
            Bundle bundle = new Bundle();
            bundle.putParcelable("Song",song);
            bundle.putString("From","AllSongs");
            play.setArguments(bundle);
            Fragment fragment =  getActivity().getSupportFragmentManager().findFragmentByTag("Play");
            if(fragment!=null)
            {
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,FragmentPlay.class,bundle,"Play").setReorderingAllowed(true).commit();
        }
    };
}
