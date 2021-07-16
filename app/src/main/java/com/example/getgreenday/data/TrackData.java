package com.example.getgreenday.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "trackData")
public class TrackData {


    @PrimaryKey
    @SerializedName("trackId")
    @ColumnInfo(name= "trackId")
    public int trackId;

    @SerializedName("trackName")
    @ColumnInfo(name = "trackName")
    public String trackName;

    @SerializedName("collectionName")
    @ColumnInfo(name = "collectionName")
    public String collectionName;

    @SerializedName("artistName")
    @ColumnInfo(name = "artistName")
    public String artistName;

    @SerializedName("artworkUrl100")
    @ColumnInfo(name = "artworkUrl")
    public String artworkUrl;

    // 즐겨찾기 여부 확인
    @ColumnInfo(name = "starred")
    public int starred; // 1(starred) | 0 (not starred)

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.artworkUrl = artworkUrl;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public void setStarred(int starred) {
        this.starred = starred;
    }

    public int getStarred() {
        return starred;
    }

      /*
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/
}



