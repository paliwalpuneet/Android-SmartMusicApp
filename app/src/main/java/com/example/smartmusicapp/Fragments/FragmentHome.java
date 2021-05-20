package com.example.smartmusicapp.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmusicapp.Adapters.AllSongsAdapter;
import com.example.smartmusicapp.Adapters.RecentItemAdapter;
import com.example.smartmusicapp.Adapters.WelcomeImageAdapter;
import com.example.smartmusicapp.Albums.AllSongs;
import com.example.smartmusicapp.DbHandler.MyDbHandler;
import com.example.smartmusicapp.MainActivity;
import com.example.smartmusicapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class FragmentHome extends Fragment {

    RecyclerView recentItemView,welcomeImages,allSongsView,diljitSongsView,honeySongsView,sidhuSongsView,karanSongsView;
    RecentItemAdapter recentItemAdapter;
    WelcomeImageAdapter welcomeImageAdapter;
    AllSongsAdapter allSongsAdapter,diljitSongAdapter,honeySinghSongAdapter,sidhuMooseWalaSongAdapter,karanAujlaSongAdapter;
//    DiljitSongAdapter diljitSongAdapter;
//    HoneySinghSongAdapter honeySinghSongAdapter;
//    SidhuMooseWalaSongAdapter sidhuMooseWalaSongAdapter;
//    KaranAujlaSongAdapter karanAujlaSongAdapter;
    LinearLayout recentLayout;
    public static View myview;
    AppCompatImageView home,search,play,menu;

    List<Integer> welcomeImageList;
    public static List<AllSongs> recentItemList;
    public List<AllSongs> favourites = new ArrayList<>();
    public ArrayList<AllSongs> allSongsList =  new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home,container,false);

        myview = view;







        recentLayout = view.findViewById(R.id.recentLayout);


        recentItemView = view.findViewById(R.id.recentItems);
        recentItemView.setHasFixedSize(true);
        recentItemView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        welcomeImages = view.findViewById(R.id.welcomeImage);
        welcomeImages.setHasFixedSize(true);
        welcomeImages.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        allSongsView = view.findViewById(R.id.allsongsview);
        allSongsView.setHasFixedSize(true);
        allSongsView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        diljitSongsView = view.findViewById(R.id.diljitSongsView);
        diljitSongsView.setHasFixedSize(true);
        diljitSongsView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        honeySongsView = view.findViewById(R.id.honeySinghSongView);
        honeySongsView.setHasFixedSize(true);
        honeySongsView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        karanSongsView = view.findViewById(R.id.karanSongView);
        karanSongsView.setHasFixedSize(true);
        karanSongsView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        sidhuSongsView = view.findViewById(R.id.sidhuSongView);
        sidhuSongsView.setHasFixedSize(true);
        sidhuSongsView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        ArrayList<AllSongs> diljitDosanjhList =(ArrayList) requireArguments().getParcelableArrayList("Diljit Songs");
        allSongsList = (ArrayList)requireArguments().getParcelableArrayList("All Songs");
        ArrayList<AllSongs> honeySinghList = (ArrayList) requireArguments().getParcelableArrayList("Honey Songs");
        ArrayList<AllSongs> sidhuMoosewalaList = (ArrayList) requireArguments().getParcelableArrayList("Sidhu Songs");
        ArrayList<AllSongs> karanList = (ArrayList) requireArguments().getParcelableArrayList("Karan Songs");

        if(allSongsList!=null) {

            allSongsAdapter = new AllSongsAdapter(getContext(), allSongsList,fragmentCommunication);
            allSongsView.setAdapter(allSongsAdapter);
        }
        if(diljitDosanjhList!=null)
        {
            diljitSongAdapter = new AllSongsAdapter(getContext(),diljitDosanjhList,fragmentCommunication);
            diljitSongsView.setAdapter(diljitSongAdapter);
        }
        if(honeySinghList!=null)
        {
            honeySinghSongAdapter = new AllSongsAdapter(getContext(),honeySinghList,fragmentCommunication);
            honeySongsView.setAdapter(honeySinghSongAdapter);
        }
        if(karanList!=null)
        {
            karanAujlaSongAdapter = new AllSongsAdapter(getContext(),karanList,fragmentCommunication);
            karanSongsView.setAdapter(karanAujlaSongAdapter);
        }
        if(sidhuMoosewalaList!=null)
        {
            sidhuMooseWalaSongAdapter = new AllSongsAdapter(getContext(),sidhuMoosewalaList,fragmentCommunication);
            sidhuSongsView.setAdapter(sidhuMooseWalaSongAdapter);
        }


        welcomeImageList = new ArrayList<>();
        setWelcomeImages();
        setRecentList();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        MyDbHandler db = new MyDbHandler(getContext());
        List<String> favouriteSongs = db.getFavourites();
        if(!favouriteSongs.isEmpty())
        {
            setFavourite(favouriteSongs);
        }











        return view;


    }

    @Override
    public void onStart() {
        super.onStart();

        home = getActivity().findViewById(R.id.home);
        play = getActivity().findViewById(R.id.play);
        search = getActivity().findViewById(R.id.search);
        menu = getActivity().findViewById(R.id.menu);

        home.setColorFilter(Color.YELLOW);
        play.setColorFilter(Color.WHITE);
        search.setColorFilter(Color.WHITE);
        menu.setColorFilter(Color.WHITE);







    }

    public void setFavourite(List<String> favouriteSongs)
    {
        for(String songName:favouriteSongs)
        {
            for(AllSongs song:allSongsList)
            {
                if(songName.equals(song.getName()))
                {
                    favourites.add(song);
                }
            }
        }
        if(!favourites.isEmpty()) {
            LinearLayout favouriteLayout = myview.findViewById(R.id.favouriteLayout);
            favouriteLayout.setVisibility(View.VISIBLE);
            RecyclerView favouriteView = myview.findViewById(R.id.favouriteItems);
            favouriteView.setHasFixedSize(true);
            favouriteView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            AllSongsAdapter favouriteAdapter = new AllSongsAdapter(getContext(), favourites, fragmentCommunication);
            favouriteView.setAdapter(favouriteAdapter);
        }
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


    public  void setRecentList()
    {
        List<AllSongs> recentList =  MainActivity.getRecentList();
        if(!recentList.isEmpty()) {
            recentItemList = recentList;
            Collections.reverse(recentItemList);
            LinearLayout layout = myview.findViewById(R.id.recentLayout);
            layout.setVisibility(View.VISIBLE);

            RecyclerView recentItemView = myview.findViewById(R.id.recentItems);
            recentItemView.setHasFixedSize(true);
            recentItemView.setLayoutManager(new LinearLayoutManager(myview.getContext(),LinearLayoutManager.HORIZONTAL,false));

            RecentItemAdapter recentItemAdapter = new RecentItemAdapter(getContext(),recentItemList,fragmentCommunication);
            recentItemView.setAdapter(recentItemAdapter);

        }
    }

    public void setWelcomeImages()
    {

        welcomeImageList.add(R.drawable.musicquote4);
        welcomeImageList.add(R.drawable.musicquote3);
        welcomeImageList.add(R.drawable.musicquote2);
        welcomeImageList.add(R.drawable.musicquote1);
        welcomeImageAdapter = new WelcomeImageAdapter(getContext(),welcomeImageList);
        welcomeImages.setAdapter(welcomeImageAdapter);
    }




}
