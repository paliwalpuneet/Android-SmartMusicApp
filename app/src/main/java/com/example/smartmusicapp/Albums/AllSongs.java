package com.example.smartmusicapp.Albums;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class AllSongs implements Parcelable {
    private String name;
    private String image;
    private String song;
    private List<AllSongs> allSongsList = new ArrayList<>();

    public AllSongs() {

    }


    public AllSongs(String name, String image, String song) {
        this.name = name;
        this.image = image;
        this.song = song;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }


    @Exclude
    public List<AllSongs> getAllSongsList() {
        return allSongsList;
    }

    public void setAllSongsList(List<AllSongs> allSongsList) {
        this.allSongsList = allSongsList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
