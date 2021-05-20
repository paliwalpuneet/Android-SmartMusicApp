package com.example.smartmusicapp.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.example.smartmusicapp.R;

public class FragmentMenu extends Fragment {

    AppCompatImageView home,search,playFr,menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu,container,false);


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
        search.setColorFilter(Color.WHITE);
        menu.setColorFilter(Color.YELLOW);
    }
}
