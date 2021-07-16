package com.example.getgreenday.data;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {

    String TAG = "DataRepository";

    private TrackDataDAO trackDataDAO;
    private LiveData<List<TrackData>> favoriteTracks;

    public DataRepository(Application application) {
        FavoriteDatabase db = FavoriteDatabase.getDatabase(application);
        trackDataDAO = db.trackDataDAO();
        favoriteTracks = trackDataDAO.getAll();

    }

    public LiveData<List<TrackData>> getFavoriteTracks() {
        return favoriteTracks;
    }

    public void insert(TrackData trackData) {
        FavoriteDatabase.databaseWriteExecutor.execute(() -> {
            trackDataDAO.insert(trackData);
        });
    }

    public void remove(TrackData trackData) {
        FavoriteDatabase.databaseWriteExecutor.execute(() -> {
            trackDataDAO.delete(trackData);
        });
    }
}