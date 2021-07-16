package com.example.getgreenday.network;

import com.example.getgreenday.data.TrackDataList;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackListRX {
    private static final String BASE_URL = "https://itunes.apple.com/";
    private static TrackListRX instance;

    public TrackListService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TrackListService.class);

    public static TrackListRX getInstance() {

        if (instance == null) {
            instance = new TrackListRX();
        }
        return instance;
    }

    public Single<TrackDataList> getTrackData(){
        return apiService.getSearchDataList("greenday", "song");
    }

}
