package com.example.getgreenday.ui.favorite;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.getgreenday.data.TrackData;
import com.example.getgreenday.data.DataRepository;

import java.util.List;


public class FavoriteViewModel extends AndroidViewModel {

    private String TAG = "FavoriteViewModel";
    private DataRepository repository;


    LiveData<List<TrackData>> favorites;

    public FavoriteViewModel(Application application) {
        super(application);
        repository = new DataRepository(application);
        favorites = repository.getFavoriteTracks();

    }

    // 목록 가져오기
    LiveData<List<TrackData>> getAllFavorites() {
        return favorites;
    }

    // 즐겨찾기 목록에서 삭제
    public void removeFromFavorites(TrackData data) {
        data.setStarred(0);
        repository.remove(data);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}


