package com.example.getgreenday.ui.tracklist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.example.getgreenday.data.TrackData;
import com.example.getgreenday.data.TrackDataList;
import com.example.getgreenday.data.DataRepository;
import com.example.getgreenday.network.TrackListRX;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class TrackListViewModel extends AndroidViewModel {

    private String TAG = "TrackListViewModel";


    private DataRepository repository;
    private final MutableLiveData<ArrayList<TrackData>> _trackData = new MutableLiveData<>();
    public LiveData<ArrayList<TrackData>> trackData = (LiveData<ArrayList<TrackData>>) _trackData;
    LiveData<List<TrackData>> favoriteData;



    public TrackListViewModel(@NonNull Application application) {
        super(application);
        getTrackDataRX();
        repository = new DataRepository(application);
        favoriteData = repository.getFavoriteTracks();
    }


    public TrackListRX apiService = TrackListRX.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();

    public void getTrackDataRX() {
        Log.e(TAG, "getTrackDataRX: ");
        disposable.add(apiService.getTrackData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TrackDataList>(){

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull TrackDataList trackDataList) {
                        Log.e(TAG, "onSuccess: ");
                        _trackData.setValue(trackDataList.dataList);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }
                })
        );
    }



    public void addToFavorites(TrackData data) {
        repository.insert(data);

    }

    public void removeFromFavorites(TrackData data) {
        repository.remove(data);
    }




    @Override
    protected void onCleared(){
        super.onCleared();
        disposable.clear();
    }
}