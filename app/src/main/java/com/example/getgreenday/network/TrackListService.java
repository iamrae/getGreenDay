package com.example.getgreenday.network;

import com.example.getgreenday.data.TrackDataList;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrackListService {

    @GET("/search")
    Single<TrackDataList> getSearchDataList(@Query("term") String term, @Query("entity") String entity);

    @GET("/search")
    Single<TrackDataList> getSearchDataPagedList(@Query("term") String term, @Query("entity") String entity, @Query("limit") int limit, @Query("offset") int offset);

}
