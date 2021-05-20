package com.example.smartmusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmusicapp.Albums.AllSongs;
import com.example.smartmusicapp.Authentication.LoginActivity;
import com.example.smartmusicapp.Authentication.StartActivity;
import com.example.smartmusicapp.DbHandler.Details;
import com.example.smartmusicapp.DbHandler.MyDbHandler;
import com.example.smartmusicapp.Fragments.FragmentHome;
import com.example.smartmusicapp.Fragments.FragmentMenu;
import com.example.smartmusicapp.Fragments.FragmentPlay;
import com.example.smartmusicapp.Fragments.FragmentSearch;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    androidx.appcompat.widget.Toolbar toolbar;
    ImageView home, search, play, menu;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference songReference = db.collection("Albums");
    DocumentReference documentReference;
//    HoneySingh honeySingh;
    List<AllSongs> honeySinghList = new ArrayList<>();
    List<AllSongs> karanAujlaList = new ArrayList<>();
    List<AllSongs> sidhuMooseWalaList = new ArrayList<>();
    List<AllSongs> diljitDosanjhList =  new ArrayList<>();
    List<AllSongs> allSongsList =  new ArrayList<>();
    FragmentManager fragmentManager;
    public static List<AllSongs> recentList = new ArrayList<>();
    public MyDbHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new MyDbHandler(this);





        fragmentManager = getSupportFragmentManager();
        home = findViewById(R.id.home);
        search = findViewById(R.id.search);
        play = findViewById(R.id.play);
        menu = findViewById(R.id.menu);

        setupPlayLists();
        setupDrawerLayout();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setPager(0);

                Fragment fragment = fragmentManager.findFragmentByTag("Home");
                if (fragment == null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("All Songs", (ArrayList) allSongsList);
                    bundle.putParcelableArrayList("Diljit Songs", (ArrayList) diljitDosanjhList);
                    bundle.putParcelableArrayList("Honey Songs", (ArrayList) honeySinghList);
                    bundle.putParcelableArrayList("Sidhu Songs", (ArrayList) sidhuMooseWalaList);
                    bundle.putParcelableArrayList("Karan Songs", (ArrayList) karanAujlaList);
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, FragmentHome.class, bundle, "Home")
                            .setReorderingAllowed(true)
                            .commit();
                }
                else
                {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("All Songs", (ArrayList) allSongsList);
                    bundle.putParcelableArrayList("Diljit Songs", (ArrayList) diljitDosanjhList);
                    bundle.putParcelableArrayList("Honey Songs", (ArrayList) honeySinghList);
                    bundle.putParcelableArrayList("Sidhu Songs", (ArrayList) sidhuMooseWalaList);
                    bundle.putParcelableArrayList("Karan Songs", (ArrayList) karanAujlaList);
                    fragmentManager.beginTransaction().show(fragment).commit();

                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setPager(1);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("All Songs",(ArrayList)allSongsList);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,FragmentSearch.class,bundle,"Search")
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  setPager(2);

                AllSongs song = new AllSongs();
                Fragment fragment = fragmentManager.findFragmentByTag("Play");
                Bundle bundle = new Bundle();
                if(!recentList.isEmpty())
                {

                    song = recentList.get(0);

                }
                bundle.putString("From", "Main");
                bundle.putParcelable("Song",song);
                if(fragment == null) {

                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, FragmentPlay.class, bundle, "Play")
                            .setReorderingAllowed(true)
                            .commit();
                }
                else
                {
                    fragmentManager.beginTransaction().show(fragment).commit();
                }
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
                else
                {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
//                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,FragmentMenu.class,null,"Menu")
//                        .setReorderingAllowed(true)
//                        .commit();
                //setPager(3);
            }
        });


    }







    @Override
    protected void onStart() {
        super.onStart();




    }

    public void setupPlayLists() {
        songReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            if (documentSnapshot.getId().equals("Honey Singh")) {


                                documentReference = documentSnapshot.getReference();
                                if (documentReference != null) {

                                    documentReference.collection("Songs").get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    for (QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots) {
                                                        //HoneySingh honeySingh = documentSnapshot1.toObject(HoneySingh.class);
                                                        //honeySinghList.add(honeySingh);
                                                        AllSongs allSongs = documentSnapshot1.toObject(AllSongs.class);
                                                        allSongsList.add(allSongs);
                                                        honeySinghList.add(allSongs);

                                                    }
                                                    //honeySingh.setHoneySinghList(honeySinghList);

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("HoneySinghList", "onFailure: Some Error Occurred while accessing the list" + e);
                                                }
                                            });
                                }
                            }
                            else if(documentSnapshot.getId().equals("Karan Aujla"))
                            {
                                documentReference = documentSnapshot.getReference();
                                if (documentReference != null) {
                                    documentReference.collection("Songs").get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    for (QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots) {
                                                        //KaranAujla karanAujla = documentSnapshot1.toObject(KaranAujla.class);
                                                        //karanAujlaList.add(karanAujla);
                                                        AllSongs allSongs = documentSnapshot1.toObject(AllSongs.class);
                                                        allSongsList.add(allSongs);
                                                        karanAujlaList.add(allSongs);
                                                    }
                                                    //for(KaranAujla k:karanAujlaList)
                                                    //Log.d("KaranAujlaList", "onSuccess:karan Aujla songs "+k.getImage());

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("KaranAujlaList", "onFailure: Some Error Occurred while accessing the list" + e);
                                                }
                                            });
                                }
                            }
                            else if(documentSnapshot.getId().equals("Sidhu MooseWala"))
                            {
                                documentReference = documentSnapshot.getReference();
                                if (documentReference != null) {
                                    documentReference.collection("Songs").get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    for (QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots) {
                                                        //SidhuMooseWala sidhuMooseWala = documentSnapshot1.toObject(SidhuMooseWala.class);
                                                        //sidhuMooseWalaList.add(sidhuMooseWala);
                                                        AllSongs allSongs = documentSnapshot1.toObject(AllSongs.class);
                                                        allSongsList.add(allSongs);
                                                        sidhuMooseWalaList.add(allSongs);
                                                    }
                                                    setupFragment(allSongsList,diljitDosanjhList,honeySinghList,karanAujlaList,sidhuMooseWalaList);
                                                  //  for(SidhuMooseWala s:sidhuMooseWalaList)
                                                    //    Log.d("Sidhu MooseWala", "onSuccess:Sidhu Moosewala songs "+s.getName());

                                                    //setupViewPager(viewPager);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("Sidhu MooseWala", "onFailure: Some Error Occurred while accessing the list" + e);
                                                }
                                            });
                                }
                            }
                            else if (documentSnapshot.getId().equals("Diljit Dosanjh"))
                            {
                                documentReference = documentSnapshot.getReference();
                                if (documentReference != null) {
                                    documentReference.collection("Songs").get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    for (QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots) {
                                                        //DiljitDosanjh diljitDosanjh = documentSnapshot1.toObject(DiljitDosanjh.class);
                                                        //diljitDosanjhList.add(diljitDosanjh);
                                                        AllSongs allSongs = documentSnapshot1.toObject(AllSongs.class);
                                                        allSongsList.add(allSongs);
                                                        diljitDosanjhList.add(allSongs);
                                                    }


                                                   // for(DiljitDosanjh d:diljitDosanjhList)
                                                     //   Log.d("Diljit Dosanjh", "onSuccess:Diljit Dosanjh songs "+d.getName());

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("Diljit Dosanjh", "onFailure: Some Error Occurred while accessing the list" + e);
                                                }
                                            });
                                }
                            }
                        }
                    }


                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("CollectionError", "onFailure: Some Error Occurred while accessing the collection" + e);
                    }
                });

    }


    public void setupFragment(List<AllSongs> list,List<AllSongs> diljitDosanjh,List<AllSongs> honey,List<AllSongs> karan,List<AllSongs> sidhu)
    {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("All Songs",(ArrayList)list);
        bundle.putParcelableArrayList("Diljit Songs",(ArrayList)diljitDosanjh);
        bundle.putParcelableArrayList("Honey Songs",(ArrayList)honey);
        bundle.putParcelableArrayList("Sidhu Songs",(ArrayList)sidhu);
        bundle.putParcelableArrayList("Karan Songs", (ArrayList)karan);
        getSupportFragmentManager().beginTransaction().addToBackStack("Home")
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainer, FragmentHome.class, bundle)
                .commit();

    }

    public static void setRecentList(AllSongs song)
    {
        if(recentList.contains(song))
        recentList.remove(song);
        recentList.add(song);
    }
    public static List<AllSongs> getRecentList()
    {
        return  recentList;
    }


    public void setupDrawerLayout()
    {

        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        View v = nav.getHeaderView(0);
        TextView name = v.findViewById(R.id.header_name);
        TextView phone = v.findViewById(R.id.header_mobile);

        Details details = dbHandler.getUser();
        if(details!=null) {
            name.setText("Welcome " + details.getFirstName() + " " + details.getLastName());
            phone.setText(details.getMobileNumber());
        }



        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.sign_out:
                        Toast.makeText(MainActivity.this, "Sign Out...", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;

                }

                return true;
            }
        });
    }



}